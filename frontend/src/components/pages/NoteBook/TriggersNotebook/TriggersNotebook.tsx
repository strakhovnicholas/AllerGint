import React, { useEffect, useState } from 'react'
import styles from './TriggersNotebook.module.css'
import ToggleTrigger from './TriggerCard/TriggerCard'
import { TriggerCard } from './ITrigger'

function TriggersNotebook() {
  const [triggers, setTriggers] = useState<TriggerCard[]>([])
  // useEffect(fetch, [addTrigger, removeTrigger])

  // (...) => triggers.push({})

  return (
    <div className="card">
      <div className={styles.triggersTitle}>
        <p className={styles.title}>Возможные триггеры</p>
        <button className={styles.triggersBtn}>+ Добавить</button>
      </div>
      <div className={styles.triggersList}>
        {[1, 2].map(() => (
          <ToggleTrigger id={1} />
        ))}
        <div className={styles.triggersListItem}></div>
      </div>
      <div className={styles.triggersActive}>
        <p className={styles.title}>Активные триггеры</p>
      </div>
    </div>
  )
}

export default TriggersNotebook
