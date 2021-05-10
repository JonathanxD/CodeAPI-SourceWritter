/*
 *      Kores-SourceWriter - Translates Kores Structure to Java Source <https://github.com/JonathanxD/Kores-SourceWriter>
 *
 *         The MIT License (MIT)
 *
 *      Copyright (c) 2018 TheRealBuggy/JonathanxD (https://github.com/JonathanxD/) <jonathan.scripter@programmer.net>
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
package com.github.jonathanxd.kores.source.process

import com.github.jonathanxd.iutils.data.TypedData
import com.github.jonathanxd.iutils.kt.require
import com.github.jonathanxd.iutils.option.Options
import com.github.jonathanxd.kores.Instruction
import com.github.jonathanxd.kores.Instructions
import com.github.jonathanxd.kores.base.*
import com.github.jonathanxd.kores.base.Annotation
import com.github.jonathanxd.kores.base.comment.*
import com.github.jonathanxd.kores.generic.GenericSignature
import com.github.jonathanxd.kores.literal.Literal
import com.github.jonathanxd.kores.operator.Operator
import com.github.jonathanxd.kores.processor.AbstractProcessorManager
import com.github.jonathanxd.kores.processor.ValidatorManager
import com.github.jonathanxd.kores.processor.VoidValidatorManager
import com.github.jonathanxd.kores.source.process.processors.*
import com.github.jonathanxd.kores.type.GenericType
import com.github.jonathanxd.kores.type.JavaType
import com.github.jonathanxd.kores.type.KoresType
import com.github.jonathanxd.kores.type.LoadedKoresType
import java.lang.reflect.Type

/**
 * Generates Java Source from CodeAPI-Base structures
 */
class PlainSourceGenerator : AbstractProcessorManager<String>() {

    override val options: Options = Options()
    override val validatorManager: ValidatorManager = VoidValidatorManager

    init {
        registerProcessor(AccessorProcessor, Accessor::class.java)
        registerProcessor(AccessProcessor, Access::class.java)
        registerProcessor(AnnotableProcessor, Annotable::class.java)
        registerProcessor(AnnotationProcessor, Annotation::class.java)
        registerProcessor(AnnotationPropertyProcessor, AnnotationProperty::class.java)
        registerProcessor(ArgumentsHolderProcessor, ArgumentsHolder::class.java)
        registerProcessor(ArrayConstructorProcessor, ArrayConstructor::class.java)
        registerProcessor(ArrayLengthProcessor, ArrayLength::class.java)
        registerProcessor(ArrayLoadProcessor, ArrayLoad::class.java)
        registerProcessor(ArrayStoreProcessor, ArrayStore::class.java)
        registerProcessor(BodyHolderProcessor, BodyHolder::class.java)
        registerProcessor(CaseProcessor, Case::class.java)
        registerProcessor(CastProcessor, Cast::class.java)
        registerProcessor(CatchBlockProcessor, CatchStatement::class.java)
        registerProcessor(KoresParameterProcessor, KoresParameter::class.java)
        registerProcessor(InstructionsProcessor, Instructions::class.java)
        registerProcessor(CodeCommentProcessor, Code::class.java)
        registerProcessor(CommentHolderProcessor, CommentHolder::class.java)
        registerProcessor(CommentsProcessor, Comments::class.java)
        registerProcessor(ConcatProcessor, Concat::class.java)
        registerProcessor(ConstructorsHolderProcessor, ConstructorsHolder::class.java)
        registerProcessor(ControlFlowProcessor, ControlFlow::class.java)
        registerProcessor(ElementsHolderProcessor, ElementsHolder::class.java)
        registerProcessor(EntryHolderProcessor, EntryHolder::class.java)
        registerProcessor(EntryProcessor, EnumEntry::class.java)
        registerProcessor(EnumValueProcessor, EnumValue::class.java)
        registerProcessor(FieldAccessProcessor, FieldAccess::class.java)
        registerProcessor(FieldDeclarationProcessor, FieldDeclaration::class.java)
        registerProcessor(FieldDefinitionProcessor, FieldDefinition::class.java)
        registerProcessor(ForEachProcessor, ForEachStatement::class.java)
        registerProcessor(ForStatementProcessor, ForStatement::class.java)
        registerProcessor(GenericSignatureHolderProcessor, GenericSignatureHolder::class.java)
        registerProcessor(GenericSignatureProcessor, GenericSignature::class.java)
        registerProcessor(GenericTypeProcessor, GenericType::class.java)
        registerProcessor(IfExpressionHolderProcessor, IfExpressionHolder::class.java)
        registerProcessor(IfExprProcessor, IfExpr::class.java)
        registerProcessor(IfGroupProcessor, IfGroup::class.java)
        registerProcessor(IfStatementProcessor, IfStatement::class.java)
        registerProcessor(ImplementationHolderProcessor, ImplementationHolder::class.java)
        registerProcessor(InnerTypesHolderProcessor, InnerTypesHolder::class.java)
        registerProcessor(InstanceOfProcessor, InstanceOfCheck::class.java)

        registerProcessorOfTypes(
            InvokeDynamicProcessor, arrayOf(
                InvokeDynamicBase::class.java,
                InvokeDynamicBase.LambdaMethodRefBase::class.java,
                InvokeDynamicBase.LambdaLocalCodeBase::class.java,

                InvokeDynamic::class.java,
                InvokeDynamic.LambdaMethodRef::class.java,
                InvokeDynamic.LambdaLocalCode::class.java
            )
        )

        registerProcessor(LabelProcessor, Label::class.java)

        registerProcessorOfTypes(
            LineProcessor, arrayOf(
                Line::class.java,
                Line.TypedLine::class.java,
                Line.NormalLine::class.java
            )
        )

        registerProcessor(LinkCommentProcessor, Link::class.java)
        registerProcessor(LiteralProcessor, Literal::class.java)

        registerProcessorOfTypes(
            MethodDeclarationProcessor, arrayOf(
                MethodDeclaration::class.java,
                ConstructorDeclaration::class.java
            )
        )

        registerProcessor(MethodInvocationProcessor, MethodInvocation::class.java)
        registerProcessor(ModifiersHolderProcessor, ModifiersHolder::class.java)
        registerProcessor(NamedProcessor, Named::class.java)
        registerProcessor(NewProcessor, New::class.java)
        registerProcessor(OperateProcessor, Operate::class.java)
        registerProcessor(OperatorProcessor, Operator::class.java)
        registerProcessor(ParametersProcessor, ParametersHolder::class.java)
        registerProcessor(ThrowsHolderProcessor, ThrowsHolder::class.java)
        registerProcessor(PlainCodeNodeProcessor, Code.CodeNode.Plain::class.java)
        registerProcessor(PlainCommentProcessor, Plain::class.java)
        registerProcessor(RepCodeNodeProcessor, Code.CodeNode.CodeRepresentation::class.java)
        registerProcessor(ReturnProcessor, Return::class.java)
        registerProcessor(ReturnTypeHolderProcessor, ReturnTypeHolder::class.java)
        registerProcessor(ScopeAccessProcessor, ScopeAccess::class.java)
        registerProcessor(StaticBlockProcessor, StaticBlock::class.java)
        registerProcessor(SuperClassHolderProcessor, SuperClassHolder::class.java)
        registerProcessor(SwitchProcessor, SwitchStatement::class.java)
        registerProcessor(SynchronizedProcessor, Synchronized::class.java)
        registerProcessor(ThrowProcessor, ThrowException::class.java)

        registerProcessorOfTypes(
            TryStatementProcessor, arrayOf(
                TryStatement::class.java,
                TryStatementBase::class.java
            )
        )

        registerProcessor(TryWithResourcesProcessor, TryWithResources::class.java)

        registerProcessorOfTypes(
            TypeDeclarationProcessor, arrayOf(
                TypeDeclaration::class.java,
                AnnotationDeclaration::class.java,
                EnumDeclaration::class.java,
                ClassDeclaration::class.java,
                InterfaceDeclaration::class.java
            )
        )

        registerProcessorOfTypes(
            TypeProcessor, arrayOf(
                Type::class.java,
                KoresType::class.java,
                JavaType::class.java,
                LoadedKoresType::class.java
            )
        )

        registerProcessor(ValueHolderProcessor, ValueHolder::class.java)
        registerProcessor(ValueProcessor, Instruction::class.java)
        registerProcessor(VariableAccessProcessor, VariableAccess::class.java)
        registerProcessor(VariableDeclarationProcessor, VariableDeclaration::class.java)
        registerProcessor(VariableDefinitionProcessor, VariableDefinition::class.java)
        registerProcessor(WhileBlockProcessor, WhileStatement::class.java)

    }

    override fun createData(): TypedData = TypedData().apply {
        APPENDER.set(this, com.github.jonathanxd.kores.source.process.JavaSourceAppender(""))
    }

    override fun <T> process(type: Class<out T>, part: T, data: TypedData): String {
        val processor = getProcessorOf(type, part, data)

        try {
            processor.process(part, data, this)
        } catch (t: Exception) {
            t.addSuppressed(IllegalStateException("Failed to process part '$part' with type '${type.simpleName}' during 'process' phase. Data map: '${data.typedDataMap}'"))
            throw t
        }

        try {
            processor.endProcess(part, data, this)
        } catch (t: Exception) {
            t.addSuppressed(IllegalStateException("Failed to process part '$part' with type '${type.simpleName}' during 'endProcess' phase. Data map: '${data.typedDataMap}'"))
            throw t
        }

        return APPENDER.require(data).toString()
    }

    override fun printFailMessage(message: String) {

    }

    override fun getFinalValue(data: TypedData): String {
        return APPENDER.require(data).toString()
    }

}