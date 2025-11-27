import React, { useEffect, useState } from 'react'
import styles from './TriggersNotebook.module.css'
import ToggleTrigger from './TriggerCard/TriggerCard'
import { TriggerCard } from './ITrigger'

function TriggersNotebook() {
  const [triggers, setTriggers] = useState<TriggerCard[]>([
    {
      id: 1,
      color: 'blue',
      name: 'Береза',
      isActive: 'Высокий уровень',
      icon: 'tree',
    },
    {
      id: 2,
      color: 'yellow',
      name: 'Тополь',
      isActive: 'Средний уровень',
      icon: 'wheat',
    },
    {
      id: 3,
      color: 'primary',
      name: 'Пыль',
      isActive: 'Низкий уровень',
      icon: 'wind',
    },
    {
      id: 4,
      color: 'green',
      name: 'Пух',
      isActive: 'Не обнаружено',
      icon: 'pagelines',
    },
  ])
  // useEffect(fetch, [addTrigger, removeTrigger])

  // (...) => triggers.push({})

  return (
    <div className={`card ${styles.cardTrigger}`}>
      <div className={styles.triggersTitle}>
        <p className={styles.title}>Возможные триггеры</p>
      </div>
      <div className={styles.triggersList}>
        {triggers.map(({ id, color, name, isActive, icon }) => (
          <ToggleTrigger
            id={id}
            color={color}
            isActive={isActive}
            name={name}
            key={id}
            icon={icon}
          />
        ))}
        <div className={styles.triggersListItem}></div>
      </div>
    </div>
  )
}

export default TriggersNotebook
