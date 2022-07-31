package com.redcpt.configurator.ui

import com.redcpt.configurator.core.Read
import com.redcpt.configurator.core.Write
import org.encryptor4j.util.FileEncryptor
import java.awt.Color
import java.awt.EventQueue
import java.awt.Font
import java.awt.event.*
import java.io.File
import java.io.IOException
import java.security.GeneralSecurityException
import java.security.NoSuchAlgorithmException
import java.security.spec.InvalidKeySpecException
import java.util.logging.Level
import java.util.logging.Logger
import javax.crypto.NoSuchPaddingException
import javax.swing.*
import javax.swing.filechooser.FileFilter

internal class CustomFilterClw : FileFilter() {
    override fun accept(file: File): Boolean {
        // Allow only directories, or files with ".txt" extension
        return file.isDirectory || file.absolutePath.endsWith(".clw")
        //    return file.isDirectory() || file.getAbsolutePath().endsWith(".dsfile");
    }

    override fun getDescription(): String {
        // This description will be displayed in the dialog,
        // hard-coded = ugly, should be done via I18N
        //return "Clown configs (*.clw)";
        return "Clown configs (*.clw)"
    }
}

class MainForm : JFrame() {
    /**
     * Creates new form main
     */
    init {
        initComponents()
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private fun initComponents() {
        jFileChooser1 = JFileChooser()
        jTextField1 = JTextField()
        jLabel1 = JLabel()
        jScrollPane1 = JScrollPane()
        jTextArea1 = JTextArea()
        jButton1 = JButton()
        jMenuBar1 = JMenuBar()
        jMenu1 = JMenu()
        jMenuItem3 = JMenuItem()
        jMenuItem1 = JMenuItem()
        jMenuItem2 = JMenuItem()
        jMenu3 = JMenu()
        jMenuItem4 = JMenuItem()
        jMenu2 = JMenu()
        jFileChooser1.dialogTitle = "Open File"
        jFileChooser1.fileFilter = CustomFilterClw()
        defaultCloseOperation = EXIT_ON_CLOSE
        jTextField1.isEditable = false
        jLabel1.font = Font("Lucida Grande", 0, 36) // NOI18N
        jLabel1.foreground = Color(0, 51, 153)
        jLabel1.text = "clearsensesConfigurator"
        jTextArea1.isEditable = false
        jTextArea1.columns = 20
        jTextArea1.rows = 5
        jScrollPane1.setViewportView(jTextArea1)
        jButton1.text = "Simpan"
        jButton1.isEnabled = false
        jButton1.addActionListener { evt -> jButton1ActionPerformed(evt) }
        jMenu1.text = "File"
        jMenuItem3.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK)
        jMenuItem3.text = "New"
        jMenuItem3.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(evt: MouseEvent) {
                jMenuItem3MouseClicked(evt)
            }
        })
        jMenuItem3.addActionListener { evt -> jMenuItem3ActionPerformed(evt) }
        jMenu1.add(jMenuItem3)
        jMenuItem1.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK)
        jMenuItem1.text = "Open"
        jMenuItem1.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(evt: MouseEvent) {
                jMenuItem1MouseClicked(evt)
            }
        })
        jMenuItem1.addActionListener { evt -> jMenuItem1ActionPerformed(evt) }
        jMenu1.add(jMenuItem1)
        jMenuItem2.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK)
        jMenuItem2.text = "Exit"
        jMenuItem2.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(evt: MouseEvent) {
                jMenuItem2MouseClicked(evt)
            }
        })
        jMenuItem2.addActionListener { evt -> jMenuItem2ActionPerformed(evt) }
        jMenu1.add(jMenuItem2)
        jMenuBar1.add(jMenu1)
        jMenu3.text = "EncryptFile"
        jMenuItem4.text = "bigFile"
        jMenuItem4.addActionListener { evt -> jMenuItem4ActionPerformed(evt) }
        jMenu3.add(jMenuItem4)
        jMenuBar1.add(jMenu3)
        jMenu2.text = "Help"
        jMenuBar1.add(jMenu2)
        jMenuBar = jMenuBar1
        val layout = GroupLayout(contentPane)
        contentPane.layout = layout
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                    layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane1)
                                .addGroup(
                                    GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(0, 0, Short.MAX_VALUE.toInt())
                                )
                                .addGroup(
                                    layout.createSequentialGroup()
                                        .addComponent(
                                            jTextField1,
                                            GroupLayout.PREFERRED_SIZE,
                                            196,
                                            GroupLayout.PREFERRED_SIZE
                                        )
                                        .addPreferredGap(
                                            LayoutStyle.ComponentPlacement.RELATED,
                                            322,
                                            Short.MAX_VALUE.toInt()
                                        )
                                        .addComponent(
                                            jButton1,
                                            GroupLayout.PREFERRED_SIZE,
                                            188,
                                            GroupLayout.PREFERRED_SIZE
                                        )
                                )
                        )
                        .addContainerGap()
                )
        )
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                    layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE.toInt())
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                            layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(
                                    jTextField1,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(jButton1)
                        )
                        .addContainerGap()
                )
        )
        pack()
    } // </editor-fold>//GEN-END:initComponents

    private fun jMenuItem2MouseClicked(evt: MouseEvent) { //GEN-FIRST:event_jMenuItem2MouseClicked
    } //GEN-LAST:event_jMenuItem2MouseClicked

    private fun jMenuItem3MouseClicked(evt: MouseEvent) { //GEN-FIRST:event_jMenuItem3MouseClicked
    } //GEN-LAST:event_jMenuItem3MouseClicked

    private fun jMenuItem1MouseClicked(evt: MouseEvent) { //GEN-FIRST:event_jMenuItem1MouseClicked
    } //GEN-LAST:event_jMenuItem1MouseClicked

    private fun jMenuItem3ActionPerformed(evt: ActionEvent) { //GEN-FIRST:event_jMenuItem3ActionPerformed
        jTextField1.isEditable = true
        jTextArea1.isEditable = true
        jTextField1.text = ""
        jTextArea1.text = ""
        simpanChecker = false
        jButton1.isEnabled = true
    } //GEN-LAST:event_jMenuItem3ActionPerformed

    private fun jMenuItem1ActionPerformed(evt: ActionEvent) { //GEN-FIRST:event_jMenuItem1ActionPerformed
        val returnVal: Int = jFileChooser1.showOpenDialog(this)
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            val file: File = jFileChooser1.selectedFile
            try {
                jTextArea1.text = Read.openFile(file.absolutePath)
            } catch (ex: NoSuchAlgorithmException) {
                Logger.getLogger(MainForm::class.java.name).log(Level.SEVERE, null, ex)
            } catch (ex: NoSuchPaddingException) {
                Logger.getLogger(MainForm::class.java.name).log(Level.SEVERE, null, ex)
            } catch (ex: InvalidKeySpecException) {
                Logger.getLogger(MainForm::class.java.name).log(Level.SEVERE, null, ex)
            } catch (ex: IOException) {
                Logger.getLogger(MainForm::class.java.name).log(Level.SEVERE, null, ex)
            } catch (ex: Exception) {
                Logger.getLogger(MainForm::class.java.name).log(Level.SEVERE, null, ex)
            }
            jTextField1.text = file.absolutePath
            jTextField1.isEditable = false
            jTextArea1.isEditable = true
            simpanChecker = true
            jButton1.isEnabled = true
        } else {
            println("File access cancelled by user.")
        }
    } //GEN-LAST:event_jMenuItem1ActionPerformed

    private fun jMenuItem2ActionPerformed(evt: ActionEvent) { //GEN-FIRST:event_jMenuItem2ActionPerformed
        System.exit(0)
    } //GEN-LAST:event_jMenuItem2ActionPerformed

    private fun jButton1ActionPerformed(evt: ActionEvent) { //GEN-FIRST:event_jButton1ActionPerformed
        try {
            if (!simpanChecker) {
                Write.makingFile(jTextArea1.text, jTextField1.text, "baru")
            } else {
                Write.makingFile(jTextArea1.text, jTextField1.text, "lama")
            }
            JOptionPane.showConfirmDialog(null, "Sukses create file config", "alert", JOptionPane.DEFAULT_OPTION)
        } catch (ex: NoSuchAlgorithmException) {
            Logger.getLogger(MainForm::class.java.name).log(Level.SEVERE, null, ex)
        } catch (ex: Exception) {
            Logger.getLogger(MainForm::class.java.name).log(Level.SEVERE, null, ex)
        }
    } //GEN-LAST:event_jButton1ActionPerformed

    private fun jMenuItem4ActionPerformed(evt: ActionEvent) { //GEN-FIRST:event_jMenuItem4ActionPerformed
        try {
            val returnVal: Int = jFileChooser1.showOpenDialog(this)
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                val srcFile: File = jFileChooser1.selectedFile
                val code: String = JOptionPane.showInputDialog(
                    null, "Masukkan nama file : ", "Authentication code ",
                    JOptionPane.INFORMATION_MESSAGE
                )
                val destFile = File(code)
                val password = "y!nDJ1nc0nF!igu12#t0r"
                val fe = FileEncryptor(password)
                fe.encrypt(srcFile, destFile)
            }
        } catch (ex: GeneralSecurityException) {
            Logger.getLogger(MainForm::class.java.name).log(Level.SEVERE, null, ex)
        } catch (ex: IOException) {
            Logger.getLogger(MainForm::class.java.name).log(Level.SEVERE, null, ex)
        }
    } //GEN-LAST:event_jMenuItem4ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private lateinit var jButton1: JButton
    private lateinit var jFileChooser1: JFileChooser
    private lateinit var jLabel1: JLabel
    private lateinit var jMenu1: JMenu
    private lateinit var jMenu2: JMenu
    private lateinit var jMenu3: JMenu
    private lateinit var jMenuBar1: JMenuBar
    private lateinit var jMenuItem1: JMenuItem
    private lateinit var jMenuItem2: JMenuItem
    private lateinit var jMenuItem3: JMenuItem
    private lateinit var jMenuItem4: JMenuItem
    private lateinit var jScrollPane1: JScrollPane
    private lateinit var jTextArea1: JTextArea
    private lateinit var jTextField1: JTextField
    // End of variables declaration//GEN-END:variables

    companion object {
        var simpanChecker = false
    }
}

