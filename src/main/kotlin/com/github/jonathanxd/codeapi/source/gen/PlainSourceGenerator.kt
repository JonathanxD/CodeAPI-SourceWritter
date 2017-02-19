/*
 *      CodeAPI-SourceWriter - Framework to generate Java code and Bytecode code. <https://github.com/JonathanxD/CodeAPI-SourceWriter>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2017 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/ & https://github.com/TheRealBuggy/) <jonathan.scripter@programmer.net>
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
import com.github.jonathanxd.codeapi.base.comment.*
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
        register(ModifiersHolder::class.java, ModifierHolderSourceGenerator)
        register(Named::class.java, NamedSourceGenerator)
        register(Keyword::class.java, KeywordSourceGenerator)
        register(TypeDeclaration::class.java, TypeDeclarationSourceGenerator)

        // Field
        register(FieldDeclaration::class.java, FieldDeclarationSourceGenerator)
        register(FieldAccess::class.java, FieldAccessSourceGenerator)
        register(FieldDefinition::class.java, FieldDefinitionSourceGenerator)

        // Variable
        register(VariableDeclaration::class.java, VariableSourceGenerator)
        register(VariableDefinition::class.java, VariableDefinitionSourceGenerator)
        register(VariableAccess::class.java, VariableAccessSourceGenerator)

        // Method
        register(MethodDeclaration::class.java, MethodDeclarationSourceGenerator)
        register(ParametersHolder::class.java, ParametersHolderSourceGenerator)
        register(CodeParameter::class.java, CodeParameterSourceGenerator)
        register(ArgumentHolder::class.java, ArgumentHolderSourceGenerator)
        register(MethodInvocation::class.java, MethodInvocationSourceGenerator)

        // ...
        register(ReturnTypeHolder::class.java, ReturnTypeHolderSourceGenerator)
        register(CodeType::class.java, CodeTypeSourceGenerator)
        register(TryStatement::class.java, TryStatementSourceGenerator)
        register(TryWithResources::class.java, TryWithResourcesSourceGenerator)
        register(CatchStatement::class.java, CatchBlockSourceGenerator)
        register(Literal::class.java, LiteralSourceGenerator)
        register(BodyHolder::class.java, BodyHolderSourceGenerator)
        register(IfStatement::class.java, IfStatementSourceGenerator)
        //register(ElseBlock::class.java, ElseBlockSourceGenerator)
        register(CodeSource::class.java, CodeSourceSourceGenerator)
        register(StaticBlock::class.java, StaticBlockSourceGenerator)
        register(Access::class.java, AccessSourceGenerator)
        registerSuper(ConstructorDeclaration::class.java, MethodDeclarationSourceGenerator)
        register(SuperClassHolder::class.java, SuperClassHolderSourceGenerator)
        //register(PackageDeclaration::class.java, PackageDeclarationSourceGenerator)
        register(ThrowException::class.java, ThrowExceptionGenerator)
        register(Return::class.java, ReturnSourceGenerator)
        register(IfExpressionHolder::class.java, IfExpressionHolderSourceGenerator)
        register(IfExpr::class.java, IfExprSourceGenerator)
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

        // Comments

        register(CommentHolder::class.java, CommentHolderSourceGenerator)
        register(Comments::class.java, CommentsSourceGenerator)
        register(Plain::class.java, PlainCommentSourceGenerator)
        register(Link::class.java, LinkCommentSourceGenerator)
        register(Code::class.java, CodeCommentSourceGenerator)
        register(Code.CodeNode.Plain::class.java, PlainCodeNodeSourceGenerator)
        register(Code.CodeNode.CodeRepresentation::class.java, RepCodeNodeSourceGenerator)

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

    private class MultiStringAppender internal constructor(delimiter: String) : ImportAppender<String>(), SimpleAppender<String> {
        override val imports = mutableListOf<CodeType>()
        private var typeDeclaration: TypeDeclaration? = null
        private var pkg: String = ""
        private val indentation = Ident(4)
        private val multiString: MultiString
        private var isAnnotation = false
        private var brackets = 0
        private var first = true
        private val isInBrackets get() = brackets > 0

        init {
            this.multiString = MultiString(delimiter) { s -> indentation.identString + s }
        }

        override fun setDeclaration(typeDeclaration: TypeDeclaration) {
            if(this.typeDeclaration == null)
                this.typeDeclaration = typeDeclaration
        }

        private fun TypeDeclaration.outerIs(type: CodeType): Boolean {
            var outer = this.outerClass

            while (outer != null && outer is TypeDeclaration && outer.outerClass != null)
                outer = outer.outerClass

            return outer?.`is`(type) ?: false
        }

        override fun appendImport(codeType: CodeType) {
            if (codeType.isPrimitive)
                return

            if (this.typeDeclaration == null)
                return


            val type = codeType.run {
                if (isArray)
                    arrayBaseComponent
                else
                    this
            }

            if (imports.contains(type))
                return

            val simple = codeType.simpleName

            val found = imports.any { it != type && it.simpleName == simple }

            if (!found)
                imports += codeType
        }

        override fun simpleAppend(t: String) {

            if(t == "\n")
                this.multiString.newLine()
            else
                this.multiString.add(t)
        }

        override fun add(elem: String) {

            if (first && elem.startsWith("package")) {
                pkg = elem
                return
            }

            first = false

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

            if (elem.startsWith("}") && elem.endsWith("{")) {
                if (!this.isAnnotation) {
                    this.multiString.newLine()
                    this.indentation.removeIdent()
                }
            }

            if (elem == "(")
                ++brackets

            if (elem == ")")
                --brackets

            if (elem != "\n")
                this.multiString.add(elem)

            if (elem == "@")
                this.isAnnotation = true

            if (elem == "\n" && this.isAnnotation) {
                this.isAnnotation = false
            }

            if (endsWithSemi) {
                if (!this.isAnnotation && !this.isInBrackets) {
                    this.multiString.newLine()
                }
            }

            if (endsWithOpenBr
                    || endsWithCloseBr) {
                if (!this.isAnnotation) {
                    this.multiString.newLine()
                }
            }

            if (elem == "\n") {
                if (!this.isAnnotation && !isInBrackets) { // Don't strip lines if element is in brackets
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

        val importsStr
            get() = imports.filter {
                if(it.packageName == "java.lang")
                    return@filter false
                if(this.typeDeclaration == null)
                    return@filter true
                if(it.`is`(this.typeDeclaration))
                    return@filter false
                if(it is TypeDeclaration && it.outerIs(this.typeDeclaration!!))
                    return@filter false

                return@filter true

            }.map { "import ${it.canonicalName};" }.let {
                return@let if (it.isNotEmpty()) "${it.joinToString("\n")}\n\n" else ""
            }

        val packageStr
            get() = if (pkg.isEmpty()) "" else "$pkg\n\n"

        override fun get(): String = "$packageStr$importsStr$multiString"
    }
}