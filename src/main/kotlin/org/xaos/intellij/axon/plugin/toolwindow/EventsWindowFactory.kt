package org.xaos.intellij.axon.plugin.toolwindow

import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBList
import com.intellij.ui.content.ContentFactory
import com.intellij.ui.treeStructure.Tree
import com.intellij.util.ui.tree.AbstractTreeModel
import org.fest.swing.data.TableCell.row
import org.jetbrains.kotlin.tools.projectWizard.wizard.ui.panel
import org.xaos.intellij.axon.plugin.AxonMessage
import org.xaos.intellij.axon.plugin.AxonModel
import org.xaos.intellij.axon.plugin.ClAxonModelRepository
import java.awt.FlowLayout
import javax.swing.JPanel
import javax.swing.tree.TreeModel

class EventsWindowFactory : ToolWindowFactory {


    override fun createToolWindowContent(
        project: Project,
        toolWindow: ToolWindow
    ) {

        val modelRepository = project.service<ClAxonModelRepository>()

        val axonModel = modelRepository.getModel()
        val treeModel = MyTreeModel(axonModel)
        val tree = Tree(treeModel)


        val contentFactory = ContentFactory.SERVICE.getInstance()
        val panel = JPanel()
        val layout = FlowLayout()
        layout.alignment = FlowLayout.LEFT
        panel.layout = layout
        panel.add(tree)


        val content =
            contentFactory.createContent(panel, "", false)

        toolWindow.contentManager.addContent(content)
    }

    override fun init(toolWindow: ToolWindow) {}


}

class MyTreeModel(private val axonModel: AxonModel) : AbstractTreeModel() {


    override fun getChild(parent: Any?, index: Int): Any {
        return when(parent){
            is AxonModel -> parent.messages[index]
            else -> "else"

        }
    }

    override fun getRoot(): Any {
        return axonModel
    }

    override fun isLeaf(node: Any?): Boolean {
        return node is AxonMessage
    }

    override fun getChildCount(parent: Any?): Int {
        return when (parent) {
            is AxonModel -> parent.messages.size
            else -> 0
        }
    }

    override fun getIndexOfChild(parent: Any?, child: Any?): Int {
        return when (parent) {
            is AxonModel -> parent.messages.indexOf(child)
            else -> 0
        }
    }

}