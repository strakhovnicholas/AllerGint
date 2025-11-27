import React from 'react'
import styles from './TriggerCard.module.css'
import { TriggerCard } from '../ITrigger'

function ToggleTrigger({ id, name, color, isActive, icon }: TriggerCard) {
  const cardStyle = {
    border: `2px solid var(--${color}-color)`,
    backgroundColor: `var(--${color}-bg-color)`,
    transition: 'all 0.3s ease',
    color: `var(--${color}-color)`,
  }

  const iconPath = `img/svg/${icon}.svg`
  // const iconPath = `img/svg/tree.svg`
  return (
    <div className={styles.triggerCard} style={cardStyle}>
      <div
        className="hard"
        style={{
          mask: `url(${iconPath}) no-repeat center`,
          WebkitMask: `url(${iconPath}) no-repeat center`,
          backgroundColor: `var(--${color}-color)`,
          width: '28px',
          height: '28px',
        }}
      ></div>

      <p className="title">{name}</p>
      <p>{isActive}</p>
    </div>
  )
}

export default ToggleTrigger
