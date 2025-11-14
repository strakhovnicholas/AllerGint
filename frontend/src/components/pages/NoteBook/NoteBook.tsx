import React from 'react'
import styles from './Notebook.module.css'
import HeaderNotebook from './HeaderNotebook/HeaderNotebook'
import HealthNotebook from './HealthNotebook/HealthNotebook'

function Notebook() {
  return (
    <div className={styles.notebookWrapper}>
      <HeaderNotebook></HeaderNotebook>
      <HealthNotebook></HealthNotebook>
    </div>
  )
}

export default Notebook
