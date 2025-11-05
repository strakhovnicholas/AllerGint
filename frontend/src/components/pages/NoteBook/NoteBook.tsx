import React from 'react'
import styles from './Notebook.module.css'
import HeaderNotebook from './HeaderNotebook'

function Notebook() {
  return (
    <div className={styles.notebook_wrapper}>
      <HeaderNotebook></HeaderNotebook>
    </div>
  )
}

export default Notebook
