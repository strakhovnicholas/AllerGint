import React from 'react'
import styles from './HealthNotebook.module.css'

function HealthNotebook() {
  return (
    <div className={`card first ${styles.healthWrapper}`}>
      <div className={styles.healthNotebookTitle}>
        <p className={styles.title}>Общее самочувствие</p>
      </div>
      <div className={styles.healthNotebookBtnsTitles}>
        <p>Плохо</p>
        <p>Отлично</p>
      </div>
      <div className={styles.healthNotebookBtns}>
        <button
          className={`${styles.healthNotebookBtn} ${styles.worse}`}
        ></button>
        <button
          className={`${styles.healthNotebookBtn} ${styles.bad}`}
        ></button>
        <button
          className={`${styles.healthNotebookBtn} ${styles.normal}`}
        ></button>
        <button
          className={`${styles.healthNotebookBtn} ${styles.good}`}
        ></button>
        <button
          className={`${styles.healthNotebookBtn} ${styles.best}`}
        ></button>
      </div>
      <p className={styles.healthNotebookBtnsSubTitles}>
        Выбрано - <span className={styles.healthNotebookBtnsRes}>это</span>
      </p>
    </div>
  )
}

export default HealthNotebook
