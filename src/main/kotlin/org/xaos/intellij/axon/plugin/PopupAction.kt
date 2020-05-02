package org.xaos.intellij.axon.plugin

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.pom.Navigatable


class PopupAction : AnAction() {

    override fun update(e: AnActionEvent) {
        // Using the event, evaluate the context, and enable or disable the action.
        val project = e.project
        e.presentation.isEnabledAndVisible = project != null
    }

    override fun actionPerformed(event: AnActionEvent) {
        // Using the event, create and show a dialog
        val currentProject: Project? = event.project
        val dlgMsg = StringBuffer(event.presentation.text.toString() + " Selected!")
        val dlgTitle: String = event.presentation.description
        // If an element is selected in the editor, add info about it.
        val nav: Navigatable? = event.getData(CommonDataKeys.NAVIGATABLE)
        if (nav != null) {
            dlgMsg.append(java.lang.String.format("\nSelected Element: %s", nav.toString()))
        }
        Messages.showMessageDialog(currentProject, dlgMsg.toString(), dlgTitle, Messages.getInformationIcon())
    }
}