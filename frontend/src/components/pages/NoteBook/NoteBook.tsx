import React from 'react'
import styles from './Notebook.module.css'
import HealthNotebook from './HealthNotebook/HealthNotebook'
import SymptomNotebook from './SymptomsNotebook/SymptomNotebook'
import PillsNotebook from './PillsNotebook/PillsNotebook'
import NotesNotebook from './NotesNotebook/NotesNotebook'
import WheatherNotebook from './WheatherNotebook/WheatherNotebook'

function Notebook() {
  return (
    <div className={styles.notebookWrapper}>
      <HealthNotebook></HealthNotebook>
      <SymptomNotebook></SymptomNotebook>
      <PillsNotebook></PillsNotebook>
      <WheatherNotebook></WheatherNotebook>
      <NotesNotebook></NotesNotebook>
    </div>
  )
}

export default Notebook
