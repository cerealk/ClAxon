package org.xaos.intellij.axon.plugin

import com.intellij.openapi.project.Project
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiReference
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.searches.ReferencesSearch
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.Query

class ClAxonModelRepository(private val project: Project) {

    private val modelFactory = ModelFactory()

    fun getModel():AxonModel{
        return modelFactory.createModel(project)
    }



}

class ModelFactory{
    fun createModel(project: Project):AxonModel {
        val javaPsiFacade = JavaPsiFacade.getInstance(project)
        val eventHandlers = getEventHandlerAnnotatedMethods(javaPsiFacade, GlobalSearchScope.allScope(project))
        val events = eventHandlers.filterNotNull().map{
            Pair(it.parameters.first().type, it)
        }.groupBy { it.first }.map {
            AxonMessage(
                it.key.toString(),
                "packege"
                , emptySet(),
                it.value.map {pair-> pair.second} .map {
                    method -> Handler(method.name)
                }.toSet()
            )
        }
        return AxonModel(project.name, events)
    }

    private fun getEventHandlerAnnotatedMethods(
        javaPsiFacade: JavaPsiFacade,
        globalSearchScope: GlobalSearchScope
    ): List<PsiMethod?> {
        val eventHandlerAnnotation = javaPsiFacade
            .findClass(
                "org.axonframework.eventsourcing.EventSourcingHandler",
                globalSearchScope
            )

        if (eventHandlerAnnotation!= null){
            val annotationUsages: Query<PsiReference> =
                ReferencesSearch.search(
                    eventHandlerAnnotation,
                    globalSearchScope
                )

            return annotationUsages.findAll().toList().map {
                val method = PsiTreeUtil.findFirstParent(
                    it.element
                ) { e -> e is PsiMethod} as PsiMethod?

                val parameter= method?.parameterList?.getParameter(0)?.type
                method
            }.filterNotNull()
        }

        return emptyList()
    }

}

data class AxonModel(val projectName: String, val messages: List<AxonMessage>)
data class AxonMessage(val name: String, val pkg: String, val emitters: Set<Emitter>, val  handlers: Set<Handler>)
data class Emitter(val className: String, val pkg: String, val method: String)
data class Handler( val method: String)