/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.groovy.scripts.internal;

import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.CodeVisitorSupport;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.ArrayExpression;
import org.codehaus.groovy.ast.expr.AttributeExpression;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.BitwiseNegationExpression;
import org.codehaus.groovy.ast.expr.BooleanExpression;
import org.codehaus.groovy.ast.expr.CastExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.ClosureListExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.expr.ElvisOperatorExpression;
import org.codehaus.groovy.ast.expr.FieldExpression;
import org.codehaus.groovy.ast.expr.GStringExpression;
import org.codehaus.groovy.ast.expr.ListExpression;
import org.codehaus.groovy.ast.expr.MapEntryExpression;
import org.codehaus.groovy.ast.expr.MapExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.MethodPointerExpression;
import org.codehaus.groovy.ast.expr.NotExpression;
import org.codehaus.groovy.ast.expr.PostfixExpression;
import org.codehaus.groovy.ast.expr.PrefixExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.RangeExpression;
import org.codehaus.groovy.ast.expr.SpreadExpression;
import org.codehaus.groovy.ast.expr.SpreadMapExpression;
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression;
import org.codehaus.groovy.ast.expr.TernaryExpression;
import org.codehaus.groovy.ast.expr.TupleExpression;
import org.codehaus.groovy.ast.expr.UnaryMinusExpression;
import org.codehaus.groovy.ast.expr.UnaryPlusExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.AssertStatement;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.BreakStatement;
import org.codehaus.groovy.ast.stmt.CaseStatement;
import org.codehaus.groovy.ast.stmt.CatchStatement;
import org.codehaus.groovy.ast.stmt.ContinueStatement;
import org.codehaus.groovy.ast.stmt.DoWhileStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.ForStatement;
import org.codehaus.groovy.ast.stmt.IfStatement;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.stmt.SwitchStatement;
import org.codehaus.groovy.ast.stmt.SynchronizedStatement;
import org.codehaus.groovy.ast.stmt.ThrowStatement;
import org.codehaus.groovy.ast.stmt.TryCatchStatement;
import org.codehaus.groovy.ast.stmt.WhileStatement;
import org.codehaus.groovy.classgen.BytecodeExpression;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.syntax.SyntaxException;

public class RestrictiveCodeVisitor extends CodeVisitorSupport {

    private final SourceUnit sourceUnit;
    private final String message;

    public RestrictiveCodeVisitor(SourceUnit sourceUnit, String message) {
        this.sourceUnit = sourceUnit;
        this.message = message;
    }

    protected void restrict(ASTNode astNode) {
        restrict(astNode, message);
    }

    protected void restrict(ASTNode astNode, String message) {
        sourceUnit.getErrorCollector().addError(
                new SyntaxException(message, astNode.getLineNumber(), astNode.getColumnNumber()),
                sourceUnit
        );
    }

    @Override
    public void visitBlockStatement(BlockStatement statement) {
        restrict(statement);
    }

    @Override
    public void visitForLoop(ForStatement forLoop) {
        restrict(forLoop);
    }

    @Override
    public void visitWhileLoop(WhileStatement loop) {
        restrict(loop);
    }

    @Override
    public void visitDoWhileLoop(DoWhileStatement loop) {
        restrict(loop);
    }

    @Override
    public void visitIfElse(IfStatement ifElse) {
        restrict(ifElse);
    }

    @Override
    public void visitExpressionStatement(ExpressionStatement statement) {
        restrict(statement);
    }

    @Override
    public void visitReturnStatement(ReturnStatement statement) {
        restrict(statement);
    }

    @Override
    public void visitAssertStatement(AssertStatement statement) {
        restrict(statement);
    }

    @Override
    public void visitTryCatchFinally(TryCatchStatement finally1) {
        restrict(finally1);
    }

    @Override
    public void visitSwitch(SwitchStatement statement) {
        restrict(statement);
    }

    @Override
    public void visitCaseStatement(CaseStatement statement) {
        restrict(statement);
    }

    @Override
    public void visitBreakStatement(BreakStatement statement) {
        restrict(statement);
    }

    @Override
    public void visitContinueStatement(ContinueStatement statement) {
        restrict(statement);
    }

    @Override
    public void visitThrowStatement(ThrowStatement statement) {
        restrict(statement);
    }

    @Override
    public void visitSynchronizedStatement(SynchronizedStatement statement) {
        restrict(statement);
    }

    @Override
    public void visitCatchStatement(CatchStatement statement) {
        restrict(statement);
    }

    @Override
    public void visitMethodCallExpression(MethodCallExpression call) {
        restrict(call);
    }

    @Override
    public void visitStaticMethodCallExpression(StaticMethodCallExpression expression) {
        restrict(expression);
    }

    @Override
    public void visitConstructorCallExpression(ConstructorCallExpression expression) {
        restrict(expression);
    }

    @Override
    public void visitTernaryExpression(TernaryExpression expression) {
        restrict(expression);
    }

    @Override
    public void visitShortTernaryExpression(ElvisOperatorExpression expression) {
        restrict(expression);
    }

    @Override
    public void visitBinaryExpression(BinaryExpression expression) {
        restrict(expression);
    }

    @Override
    public void visitPrefixExpression(PrefixExpression expression) {
        restrict(expression);
    }

    @Override
    public void visitPostfixExpression(PostfixExpression expression) {
        restrict(expression);
    }

    @Override
    public void visitBooleanExpression(BooleanExpression expression) {
        restrict(expression);
    }

    @Override
    public void visitClosureExpression(ClosureExpression expression) {
        restrict(expression);
    }

    @Override
    public void visitTupleExpression(TupleExpression expression) {
        restrict(expression);
    }

    @Override
    public void visitMapExpression(MapExpression expression) {
        restrict(expression);
    }

    @Override
    public void visitMapEntryExpression(MapEntryExpression expression) {
        restrict(expression);
    }

    @Override
    public void visitListExpression(ListExpression expression) {
        restrict(expression);
    }

    @Override
    public void visitRangeExpression(RangeExpression expression) {
        restrict(expression);
    }

    @Override
    public void visitPropertyExpression(PropertyExpression expression) {
        restrict(expression);
    }

    @Override
    public void visitAttributeExpression(AttributeExpression attributeExpression) {
        restrict(attributeExpression);
    }

    @Override
    public void visitFieldExpression(FieldExpression expression) {
        restrict(expression);
    }

    @Override
    public void visitMethodPointerExpression(MethodPointerExpression expression) {
        restrict(expression);
    }

    @Override
    public void visitConstantExpression(ConstantExpression expression) {
        restrict(expression);
    }

    @Override
    public void visitClassExpression(ClassExpression expression) {
        restrict(expression);
    }

    @Override
    public void visitVariableExpression(VariableExpression expression) {
        restrict(expression);
    }

    @Override
    public void visitDeclarationExpression(DeclarationExpression expression) {
        restrict(expression);
    }

    @Override
    public void visitGStringExpression(GStringExpression expression) {
        restrict(expression);
    }

    @Override
    public void visitArrayExpression(ArrayExpression expression) {
        restrict(expression);
    }

    @Override
    public void visitSpreadExpression(SpreadExpression expression) {
        restrict(expression);
    }

    @Override
    public void visitSpreadMapExpression(SpreadMapExpression expression) {
        restrict(expression);
    }

    @Override
    public void visitNotExpression(NotExpression expression) {
        restrict(expression);
    }

    @Override
    public void visitUnaryMinusExpression(UnaryMinusExpression expression) {
        restrict(expression);
    }

    @Override
    public void visitUnaryPlusExpression(UnaryPlusExpression expression) {
        restrict(expression);
    }

    @Override
    public void visitBitwiseNegationExpression(BitwiseNegationExpression expression) {
        restrict(expression);
    }

    @Override
    public void visitCastExpression(CastExpression expression) {
        restrict(expression);
    }

    @Override
    public void visitArgumentlistExpression(ArgumentListExpression expression) {
        restrict(expression);
    }

    @Override
    public void visitClosureListExpression(ClosureListExpression closureListExpression) {
        restrict(closureListExpression);
    }

    @Override
    public void visitBytecodeExpression(BytecodeExpression expression) {
        restrict(expression);
    }
}
