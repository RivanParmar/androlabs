package com.android.manifmerger

import com.android.ide.common.blame.Message
import com.android.ide.common.blame.SourceFile
import com.android.ide.common.blame.SourceFilePosition
import com.android.ide.common.blame.SourcePosition
import com.google.common.base.Joiner
import com.google.common.base.MoreObjects
import com.google.common.base.Throwables
import com.google.common.collect.ImmutableList
import com.google.common.collect.Lists
import org.xml.sax.SAXParseException
import java.io.File


/**
 * Exception for errors during merging.
 */
class MergingException protected constructor(cause: Throwable?, vararg messages: Message) :
    Exception(if (messages.size == 1) messages[0].text else MULTIPLE_ERRORS, cause) {

    private val mMessages: List<Message>

    val messages: List<Message>
        get() = mMessages

    init {
        mMessages = ImmutableList.copyOf(messages)
    }

    class Builder internal constructor() {
        private var mCause: Throwable? = null

        private var mMessageText: String? = null

        private var mOriginalMessageText: String? = null

        private var mFile: SourceFile = SourceFile.UNKNOWN

        private var mPosition: SourcePosition = SourcePosition.UNKNOWN

        fun wrapException(cause: Throwable): Builder {
            mCause = cause
            mOriginalMessageText = Throwables.getStackTraceAsString(cause)
            return this
        }

        fun withFile(file: File): Builder {
            mFile = SourceFile(file)
            return this
        }

        fun withFile(file: SourceFile): Builder {
            mFile = file
            return this
        }

        fun withPosition(position: SourcePosition): Builder {
            mPosition = position
            return this
        }

        fun withMessage(
            messageText: String,
            vararg args: Any
        ): Builder {
            mMessageText = if (args.isEmpty()) messageText else String.format(messageText, *args)
            return this
        }

        fun build(): MergingException {
            if (mCause != null) {
                if (mMessageText == null) {
                    mMessageText = MoreObjects.firstNonNull(
                        mCause!!.localizedMessage,
                        mCause!!.javaClass.canonicalName
                    )
                }
                if (mPosition === SourcePosition.UNKNOWN && mCause is SAXParseException) {
                    val exception: SAXParseException = mCause as SAXParseException
                    val lineNumber: Int = exception.lineNumber
                    if (lineNumber != -1) {
                        // Convert positions to be 0-based for SourceFilePosition.
                        mPosition = SourcePosition(
                            lineNumber - 1,
                            exception.columnNumber - 1, -1
                        )
                    }
                }
            }
            if (mMessageText == null) {
                mMessageText = "Unknown error."
            }
            return MergingException(
                mCause,
                Message(
                    Message.Kind.ERROR,
                    mMessageText!!,
                    MoreObjects.firstNonNull(mOriginalMessageText, mMessageText!!),
                    RESOURCE_ASSET_MERGER_TOOL_NAME,
                    SourceFilePosition(mFile, mPosition)
                )
            )
        }
    }

    companion object {
        const val MULTIPLE_ERRORS = "Multiple errors:"
        const val RESOURCE_ASSET_MERGER_TOOL_NAME = "Resource and asset merger"

        fun wrapException(cause: Throwable): Builder = Builder().wrapException(cause)

        fun withMessage(message: String, vararg args: Any?) = Builder().withMessage(message, args)
    }

    /**
     * Computes the error message to display for this error
     */
    override val message: String?
        get() {
            val messages: MutableList<String> = Lists.newArrayListWithCapacity(mMessages.size)
            for (message in mMessages) {
                val sb = StringBuilder()
                val sourceFilePositions = message.sourceFilePositions
                if (sourceFilePositions.size > 1 || sourceFilePositions[0] !=
                    SourceFilePosition.UNKNOWN
                ) {
                    sb.append(Joiner.on('\t').join(sourceFilePositions))
                }
                var text = message.text
                if (sb.isNotEmpty()) {
                    sb.append(':').append(' ')

                    // ALWAYS insert the string "Error:" between the path and the message.
                    // This is done to make the error messages more simple to detect
                    // (since a generic path: message pattern can match a lot of output, basically
                    // any labeled output, and we don't want to do file existence checks on any random
                    // string to the left of a colon.)
                    if (!text.startsWith("Error: ")) {
                        sb.append("Error: ")
                    }
                } else if (!text.contains("Error: ")) {
                    sb.append("Error: ")
                }

                // If the error message already starts with the path, strip it out.
                // This avoids redundant looking error messages you can end up with
                // like for example for permission denied errors where the error message
                // string itself contains the path as a prefix:
                //    /my/full/path: /my/full/path (Permission denied)
                if (sourceFilePositions.size == 1) {
                    val file = sourceFilePositions[0].file.sourceFile
                    if (file != null) {
                        val path = file.absolutePath
                        if (text.startsWith(path)) {
                            var stripStart = path.length
                            if (text.length > stripStart && text[stripStart] == ':') {
                                stripStart++
                            }
                            if (text.length > stripStart && text[stripStart] == ' ') {
                                stripStart++
                            }
                            text = text.substring(stripStart)
                        }
                    }
                }
                sb.append(text)
                messages.add(sb.toString())
            }
            return Joiner.on('\n').join(messages)
        }

    override fun toString(): String = message!!
}