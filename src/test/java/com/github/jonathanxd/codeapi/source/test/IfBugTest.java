package com.github.jonathanxd.codeapi.source.test;

import com.github.jonathanxd.codeapi.CodeSource;
import com.github.jonathanxd.codeapi.base.IfExpr;
import com.github.jonathanxd.codeapi.base.IfStatement;
import com.github.jonathanxd.codeapi.factory.Factories;
import com.github.jonathanxd.codeapi.literal.Literals;
import com.github.jonathanxd.codeapi.operator.Operators;
import com.github.jonathanxd.codeapi.source.process.PlainSourceGenerator;

import org.junit.Test;

public class IfBugTest {

    @Test
    public void ifBugTest() {
        IfStatement x = IfStatement.Builder.builder()
                .expressions(new IfExpr(Factories.accessVariable(Boolean.TYPE, "b"), Operators.NOT_EQUAL_TO, Literals.FALSE))
                .body(CodeSource.empty())
                .elseStatement(CodeSource.empty())
                .build();

        PlainSourceGenerator generator = new PlainSourceGenerator();

        String process = generator.process(x);
        new SourceTest(process)
                .expect("if (b) {\n" +
                        "}\n");
    }

}
