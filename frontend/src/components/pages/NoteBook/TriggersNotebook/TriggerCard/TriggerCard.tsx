import React from 'react'
import styles from './TriggerCard.module.css'
import { TriggerCard } from '../ITrigger'

function ToggleTrigger({ id, name, color, isActive }: TriggerCard) {
  return <div className={styles.triggerCard}>{id}</div>
}

export default ToggleTrigger
