/*
 *      CodeAPI-SourceWriter - Framework to generate Java code and Bytecode code. <https://github.com/JonathanxD/CodeAPI-SourceWriter>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2016 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/ & https://github.com/TheRealBuggy/) <jonathan.scripter@programmer.net>
 *      Copyright (c) contributors
 *
 *
 *      Permission is hereby granted, free of charge, to any person obtaining a copy
 *      of this software and associated documentation files (the "Software"), to deal
 *      in the Software without restriction, including without limitation the rights
 *      to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *      copies of the Software, and to permit persons to whom the Software is
 *      furnished to do so, subject to the following conditions:
 *
 *      The above copyright notice and this permission notice shall be included in
 *      all copies or substantial portions of the Software.
 *
 *      THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *      IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *      FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *      AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *      LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *      OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *      THE SOFTWARE.
 */
package com.github.jonathanxd.codeapi.source.gen

import com.github.jonathanxd.codeapi.CodePart
import com.github.jonathanxd.codeapi.CodeSource
import com.github.jonathanxd.codeapi.base.*
import com.github.jonathanxd.codeapi.base.Annotation
import com.github.jonathanxd.codeapi.common.CodeParameter
import com.github.jonathanxd.codeapi.gen.Appender
import com.github.jonathanxd.codeapi.gen.value.AbstractGenerator
import com.github.jonathanxd.codeapi.gen.value.ValueGenerator
import com.github.jonathanxd.codeapi.generic.GenericSignature
import com.github.jonathanxd.codeapi.keyword.Keyword
import com.github.jonathanxd.codeapi.literal.Literal
import com.github.jonathanxd.codeapi.source.gen.generator.*
import com.github.jonathanxd.codeapi.source.util.Ident
import com.github.jonathanxd.codeapi.source.util.MultiString
import com.github.jonathanxd.codeapi.sugar.SugarSyntax
import com.github.jonathanxd.codeapi.type.CodeType
import com.github.jonathanxd.codeapi.type.GenericType
import com.github.jonathanxd.iutils.option.Options

class PlainSourceGenerator : AbstractGenerator<String, PlainSourceGenerator>() {

    override val registry: MutableMap<Class<*>, ValueGenerator<*, String, PlainSourceGenerator>> = mutableMapOf<Class<*>, ValueGenerator<*, String, PlainSourceGenerator>>()
    override val options: Options = Options()


    init {

        register(ImplementationHolder::class.java, ImplementationHolderSourceGenerator)
        register(ModifiersHolder::class.java, ModifierSourceGenerator)
        register(Named::class.java, NamedSourceGenerator)
        register(Keyword::class.java, KeywordSourceGenerator)
        register(TypeDeclaration::class.java, TypeSourceGenerator)

        // Field
        register(FieldDeclaration::class.java, FieldSourceGenerator)
        register(FieldAccess::class.java, FieldAccessSourceGenerator)

        // ...
        register(VariableDeclaration::class.java, VariableSourceGenerator)
        register(MethodDeclaration::class.java, MethodSourceGenerator)
        register(ReturnTypeHolder::class.java, ReturnableSourceGenerator)
        register(CodeType::class.java, CodeTypeSourceGenerator)
        register(ParametersHolder::class.java, ParameterizableSourceGenerator)
        register(CodeParameter::class.java, CodeParameterSourceGenerator)
        register(TryStatement::class.java, TryBlockSourceGenerator)
        register(CatchStatement::class.java, CatchBlockSourceGenerator)
        register(Literal::class.java, LiteralSourceGenerator)
        register(BodyHolder::class.java, BodyHolderSourceGenerator)
        register(IfStatement::class.java, IfStatementSourceGenerator)
        //register(ElseBlock::class.java, ElseBlockSourceGenerator)
        register(CodeSource::class.java, CodeSourceSourceGenerator)
        register(StaticBlock::class.java, StaticBlockSourceGenerator)
        register(Access::class.java, AccessSourceGenerator)
        registerSuper(ConstructorDeclaration::class.java, MethodSourceGenerator)
        register(SuperClassHolder::class.java, SuperClassHolderSourceGenerator)
        //register(PackageDeclaration::class.java, PackageDeclarationSourceGenerator)
        register(ThrowException::class.java, ThrowExceptionGenerator)
        register(Return::class.java, ReturnSourceGenerator)
        register(IfExpressionHolder::class.java, IfExpressionableSourceGenerator)
        register(IfExpr::class.java, IfExprSourceGenerator)
        register(VariableDeclaration::class.java, VariableStoreSourceGenerator)
        register(ArrayConstructor::class.java, ArrayConstructorSourceGenerator)
        register(ArrayLoad::class.java, ArrayLoadSourceGenerator)
        register(ArrayStore::class.java, ArrayStoreSourceGenerator)
        register(ArrayLength::class.java, ArrayLengthSourceGenerator)
        //register(TagLine::class.java, TagLineSourceGenerator)
        register(Operate::class.java, OperateSourceGenerator)
        //register(VariableOperate::class.java, VariableOperateSourceGenerator)
        //register(ClassType::class.java, ClassTypeSourceGenerator)
        register(Accessor::class.java, AccessorSourceGenerator)

        // While & Do
        //register(DoWhileBlock::class.java, DoWhileBlockSourceGenerator)
        register(WhileStatement::class.java, WhileBlockSourceGenerator)
        //register(SimpleWhileBlock::class.java, SimpleWhileBlockSourceGenerator)
        register(ForStatement::class.java, ForStatementSourceGenerator)
        register(ForEachStatement::class.java, ForEachSourceGenerator)

        // Method body
        register(MethodSpecification::class.java, MethodSpecificationSourceGenerator)
        register(ArgumentHolder::class.java, ArgumenterizableSourceGenerator)
        register(VariableAccess::class.java, VariableAccessSourceGenerator)
        register(MethodInvocation::class.java, MethodInvocationSourceGenerator)

        // Cast
        register(Cast::class.java, CastPartSourceGenerator)

        // Generics
        register(GenericSignatureHolder::class.java, GenericHolderSourceGenerator)
        register(GenericSignature::class.java, GenericSignatureSourceGenerator)
        register(GenericType::class.java, GenericTypeSourceGenerator)

        // Annotation
        register(Annotable::class.java, AnnotableSourceGenerator)
        register(Annotation::class.java, AnnotationSourceGenerator)
        register(EnumValue::class.java, EnumValueSourceGenerator)

        // Try-with-resources
        registerSuper(TryWithResources::class.java, TryBlockSourceGenerator)

        // Instance Of
        register(InstanceOfCheck::class.java, InstanceOfSourceGenerator)

        // Label
        register(Label::class.java, LabelSourceGenerator)

        // Control flow.
        register(ControlFlow::class.java, ControlFlowSourceGenerator)
        //register(Continue::class.java, ContinueSourceGenerator)

        // Switch & Case
        register(SwitchStatement::class.java, SwitchSourceGenerator)
        register(Case::class.java, CaseSourceGenerator)

        // Enum & Enum Entries
        register(EnumDeclaration::class.java, EnumSourceGenerator)
        register(EntryHolder::class.java, EntryHolderSourceGenerator)
        register(EnumEntry::class.java, EntrySourceGenerator)

        // Annotation & Annotation Property
        register(AnnotationDeclaration::class.java, AnnotationTypeSourceGenerator)
        register(AnnotationProperty::class.java, AnnotationPropertySourceGenerator)

        // Concat
        register(Concat::class.java, ConcatSourceGenerator)

    }

    override fun <T : CodePart, R : CodePart> registerSugarSyntax(type: Class<T>, sugarSyntax: SugarSyntax<T, R>): SugarSyntax<*, *>? {
        var syntax: SugarSyntax<*, *>? = null

        if (this.registry.containsKey(type)) {
            val generator = this.registry[type]

            if (generator is SugarSyntaxGenerator<*, *>)
                syntax = generator.sugarSyntax
        }

        this.registry.put(type, SugarSyntaxGenerator(sugarSyntax))

        return syntax
    }

    private fun <T> register(tClass: Class<T>, generator: ValueGenerator<T, String, PlainSourceGenerator>) {
        registry.put(tClass, generator)
    }

    private fun <T> registerSuper(tClass: Class<T>, generator: ValueGenerator<T, String, PlainSourceGenerator>) {
        registry.put(tClass, generator)
    }

    override fun createAppender(): Appender<String> = MultiStringAppender(delimiter = " ")

    private class MultiStringAppender internal constructor(delimiter: String) : Appender<String>() {
        private val indentation = Ident(4)
        private val multiString: MultiString
        private var isAnnotation = false

        init {
            this.multiString = MultiString(delimiter) { s -> indentation.identString + s }
        }

        override fun add(elem: String) {
            @Suppress("NAME_SHADOWING")
            var elem = elem

            if (elem.isEmpty())
                return

            val endsWithSemi = elem.endsWith(";")
            val endsWithOpenBr = elem.endsWith("{")
            val endsWithCloseBr = elem.endsWith("}")

            if (endsWithCloseBr) {
                elem = elem.substring(0, elem.length - 1)
            }

            if (elem != "\n")
                this.multiString.add(elem)

            if (elem == "@")
                this.isAnnotation = true

            if (elem == "\n" && this.isAnnotation) {
                this.isAnnotation = false
            }

            if (endsWithSemi
                    || endsWithOpenBr
                    || endsWithCloseBr
                    || elem == "\n") {
                if (!this.isAnnotation) {
                    this.multiString.newLine()
                }
            }

            if (endsWithOpenBr) {
                if (!this.isAnnotation) {
                    this.indentation.addIdent()
                }
            }

            if (endsWithCloseBr) {
                if (!this.isAnnotation) {
                    this.indentation.removeIdent()
                    this.multiString.add("}")
                    this.multiString.newLine()
                    this.multiString.newLine()
                } else {
                    this.multiString.add("}")
                }
            }
        }

        override fun get(): String {
            return this.multiString.toString()
        }
    }
}