package org.cytoscape.sample.internal

import java.util.Properties
import java.awt.event.ActionEvent
import scala.collection.JavaConversions._

import org.osgi.framework.BundleContext

import org.cytoscape.service.util.AbstractCyActivator
import org.cytoscape.application.swing.{CySwingApplication, CytoPanelName, CyAction}

// MyCytoPanel
import javax.swing.{JPanel, JLabel}
import org.cytoscape.application.swing.CytoPanelComponent

// Sample02
import org.cytoscape.application.swing.{AbstractCyAction, CytoPanelState}


class CyActivator extends AbstractCyActivator {
  def start(bc: BundleContext) {
    println("Starting Sample02 plugin")
    val cytoscapeDesktopService = getService(bc, classOf[CySwingApplication])
    val myCytoPanel = new MyCytoPanel
    val sample02Action = new Sample02(cytoscapeDesktopService, myCytoPanel)
    registerService(bc, myCytoPanel, classOf[CytoPanelComponent], new Properties)
    registerService(bc, sample02Action, classOf[CyAction], new Properties)
  }
}

@SerialVersionUID(8292806967891823933L)
class MyCytoPanel extends JPanel with CytoPanelComponent {

  val lbXYZ = new JLabel("This is my Panel")
  add(lbXYZ)
  setVisible(true)

  def getComponent = this
  def getCytoPanelName = CytoPanelName.WEST
  def getTitle = "MyPanel"
  def getIcon = null
}

class Sample02(desktopApp: CySwingApplication, myCytoPanel: MyCytoPanel)
extends AbstractCyAction("sample02") {
  setPreferredMenu("Apps")
  val cytoPanelWest = desktopApp.getCytoPanel(CytoPanelName.WEST)

  def actionPerformed(e: ActionEvent) {
    // if the state of the cytoPanelWest is HIDE, show it
    if (cytoPanelWest.getState == CytoPanelState.HIDE) {
      cytoPanelWest.setState(CytoPanelState.DOCK)
    }
    // select my panel
    val index = cytoPanelWest.indexOfComponent(myCytoPanel)
    if (index != -1) {
      cytoPanelWest.setSelectedIndex(index)
    }
  }
}
