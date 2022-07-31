package com.redcpt.configurator.main

import com.redcpt.configurator.ui.MainForm
import java.awt.EventQueue
import java.util.logging.Level
import java.util.logging.Logger
import javax.swing.UIManager
import javax.swing.UnsupportedLookAndFeelException

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            try {
                for (info in UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus" == info.name) {
                        UIManager.setLookAndFeel(info.className)
                        break
                    }
                }
            } catch (ex: ClassNotFoundException) {
                Logger.getLogger(MainForm::class.java.name).log(Level.SEVERE, null, ex)
            } catch (ex: InstantiationException) {
                Logger.getLogger(MainForm::class.java.name).log(Level.SEVERE, null, ex)
            } catch (ex: IllegalAccessException) {
                Logger.getLogger(MainForm::class.java.name).log(Level.SEVERE, null, ex)
            } catch (ex: UnsupportedLookAndFeelException) {
                Logger.getLogger(MainForm::class.java.name).log(Level.SEVERE, null, ex)
            }
            //</editor-fold>

            /* Create and display the form */
            EventQueue.invokeLater { MainForm().isVisible = true }
        }
    }
}