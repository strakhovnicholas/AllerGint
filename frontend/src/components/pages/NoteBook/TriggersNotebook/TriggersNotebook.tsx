import React from 'react'
import styles from './TriggersNotebook.module.css'

function TriggersNotebook() {
  return (
    <div className="card">
      <div className={styles.triggersTitle}>
        <p className={styles.title}>Симптомы</p>
        <button className={styles.triggersBtn}>+ Добавить</button>
      </div>
      <div className={styles.triggersList}>
        <div className={styles.triggersListItem}></div>
      </div>
      <div className={styles.triggersActive}>
        <p className={styles.title}>Активные триггеры</p>
      </div>
    </div>
  )
}

export default TriggersNotebook
