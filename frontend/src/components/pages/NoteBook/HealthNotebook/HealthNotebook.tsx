import React, {useEffect, useState} from 'react'
import styles from './HealthNotebook.module.css'
import {Day} from '../../../../interfaces/Notebook-interface'

function HealthNotebook() {
    const [day, setDay] = useState<Day | null>(null)
    const [selectedHealth, setSelectedHealth] = useState<number | null>(null)

    useEffect(() => {
        const userId = 'b1d60e91-97e6-4659-ae7f-bde6808e2c4c'
        const timestamp = new Date().toISOString()

        fetch(`http://localhost:8080/api/diary/day?timestamp=${timestamp}`, {
            headers: {'X-User-Id': userId}
        })
            .then(res => res.json())
            .then(data => {
                setDay(data)
                setSelectedHealth(data?.healthState ? data.healthState : null)
            })
            .catch(err => console.error(err))

    }, [])

    const healthLevels = [
        {id: 1, label: 'Плохо'},
        {id: 2, label: 'Ниже среднего'},
        {id: 3, label: 'Нормально'},
        {id: 4, label: 'Хорошо'},
        {id: 5, label: 'Отлично'},
    ]

    return (
        <div className={`card ${styles.cardHealth}`}>
            <div className={styles.healthTitle}><p className="title">Общее самочувствие</p></div>

            <div className={styles.healthButtons}>
                {healthLevels.map(level => (
                    <button
                        key={level.id}
                        className={`${styles.healthBtn} ${
                            selectedHealth === level.id ? styles.healthBtnActive : ''
                        }`}
                        onClick={() => setSelectedHealth(level.id)}
                    >
                        {level.label}
                    </button>
                ))}
            </div>

            {day?.timestamp && (
                <p className={styles.healthTimestamp}>
                    Выбрано: <span>{day.healthState || 'не указано'}</span>
                </p>
            )}
        </div>

    )
}

export default HealthNotebook
