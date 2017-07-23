package com.github.jonathanxd.codeapi.source.process.processors

import com.github.jonathanxd.codeapi.base.Scope
import com.github.jonathanxd.codeapi.base.ScopeAccess
import com.github.jonathanxd.codeapi.processor.ProcessorManager
import com.github.jonathanxd.codeapi.processor.processAs
import com.github.jonathanxd.codeapi.source.process.AppendingProcessor
import com.github.jonathanxd.codeapi.source.process.JavaSourceAppender
import com.github.jonathanxd.iutils.data.TypedData

object ScopeAccessProcessor : AppendingProcessor<ScopeAccess> {

    override fun process(part: ScopeAccess, data: TypedData, processorManager: ProcessorManager<*>, appender: JavaSourceAppender) {
        when (part.scope) {
            Scope.OUTER -> {
                processorManager.processAs(part.type, data)
                appender += ".this"
            }
            else -> {
            }
        }
    }

}