import React, { useEffect } from 'react'
import styles from './SymptomNotebook.module.css'
import { useState } from 'react'
import { Day } from '../../../../interfaces/Notebook-interface'

type SymptomStatus = null | 'strong' | 'weak' | 'moderate'

interface StatusButton {
  id: SymptomStatus
  label: string
}

interface SymptomCard {
  id: string
  name: string
  status: SymptomStatus
  clickTime: Date | null
}

interface Symptom {
  name: string
  dosage: string
}

function SymptomNotebook() {
  const [symptoms, setSymptoms] = useState<Day | null>(null)
  const [activeButtons, setActiveButtons] = useState<{
    [key: string]: SymptomStatus
  }>({})
  const [clickTimes, setClickTimes] = useState<{ [key: string]: Date | null }>(
    {}
  )

  const statusButtons: StatusButton[] = [
    { id: 'strong', label: 'Сильно' },
    { id: 'moderate', label: 'Умеренно' },
    { id: 'weak', label: 'Слабо' },
  ]

  const handleStatusClick = (symptomId: string, status: SymptomStatus) => {
    const now = new Date()
    setActiveButtons((prev) => ({
      ...prev,
      [symptomId]: status,
    }))
    setClickTimes((prev) => ({
      ...prev,
      [symptomId]: now,
    }))
  }

  const getButtonClassName = (symptomId: string, status: SymptomStatus) => {
    return `${styles.symptomListItemStatusBtn} ${
      activeButtons[symptomId] === status
        ? styles.symptomListItemStatusBtnActive
        : ''
    }`
  }

  const formatTime = (date: Date): string => {
    return date.toLocaleTimeString('ru-RU', {
      hour: '2-digit',
      minute: '2-digit',
    })
  }

  useEffect(() => {
    fetch('http://localhost:8080/api/diary/day')
      .then((res) => res.json())
      .then((data) => setSymptoms(data))
      .catch((err) => console.log(err))
  }, [])

  return (
    <div className={`card ${styles.cardSymptom}`}>
      <div className={styles.symptomTitle}>
        <p className={styles.title}>Симптомы</p>
        <button className={styles.symptomBtn}>+ Добавить</button>
      </div>

      {symptoms ? (
        symptoms?.medicines.map((symptom: Symptom, id) => (
          <div key={id} className={styles.symptomListItem}>
            <div className={styles.symptomListItemTitle}>
              <svg
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 640 640"
                width="15px"
              >
                <path d="M320 576C214 576 128 490 128 384C128 292.8 258.2 109.9 294.6 60.5C300.5 52.5 309.8 48 319.8 48L320.2 48C330.2 48 339.5 52.5 345.4 60.5C381.8 109.9 512 292.8 512 384C512 490 426 576 320 576zM240 376C240 362.7 229.3 352 216 352C202.7 352 192 362.7 192 376C192 451.1 252.9 512 328 512C341.3 512 352 501.3 352 488C352 474.7 341.3 464 328 464C279.4 464 240 424.6 240 376z" />
              </svg>
              <p className={styles.title}>{symptom.name}</p>
              <button>
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  viewBox="0 0 640 640"
                  width="10px"
                >
                  <path d="M183.1 137.4C170.6 124.9 150.3 124.9 137.8 137.4C125.3 149.9 125.3 170.2 137.8 182.7L275.2 320L137.9 457.4C125.4 469.9 125.4 490.2 137.9 502.7C150.4 515.2 170.7 515.2 183.2 502.7L320.5 365.3L457.9 502.6C470.4 515.1 490.7 515.1 503.2 502.6C515.7 490.1 515.7 469.8 503.2 457.3L365.8 320L503.1 182.6C515.6 170.1 515.6 149.8 503.1 137.3C490.6 124.8 470.3 124.8 457.8 137.3L320.5 274.7L183.1 137.4z" />
                </svg>
              </button>
            </div>
            <div className={styles.symptomListItemStatus}>
              {statusButtons.map((button) => (
                <button
                  key={button.id}
                  className={getButtonClassName(symptom.name, button.id)}
                  onClick={() => handleStatusClick(symptom.name, button.id)}
                >
                  {button.label}
                </button>
              ))}
            </div>
            <div className={styles.symptomListItemTime}>
              <p>
                Время:{' '}
                <span>
                  {clickTimes[id] ? formatTime(clickTimes[id]!) : '--:--'}
                </span>
              </p>
            </div>
          </div>
        ))
      ) : (
        <div>Загрузка...</div>
      )}
    </div>
  )
}

export default SymptomNotebook
