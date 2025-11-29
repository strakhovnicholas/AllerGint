import { useEffect, useState } from 'react'
import styles from './HeaderNotebook.module.css'
import { formatDateTime } from '../../../../utils/formatDateTime'

interface MedicineDto {
    id: string
    medicineName: string
    medicineDescription: string | null
    medicineType: string
    dose: number
    doseMeasureType: string
}

interface SymptomDto {
    id: string
    symptomName: string
    timestamp: string | null
    symptomState: 'STRONG' | 'MEDIUM' | 'WEAK'
}

interface DiaryPageDto {
    id: string
    userId: string
    healthState: string
    timestamp: string
    medicines: MedicineDto[]
    userSymptoms: SymptomDto[]
    userNotes: string
    aiNotes: string
}

const getDay = (date: Date): string =>
    date.toLocaleString('ru-RU', { weekday: 'long' })

function HeaderNotebook() {
    const [page, setPage] = useState<DiaryPageDto | null>(null)
    const userId = 'b1d60e91-97e6-4659-ae7f-bde6808e2c4c'

    useEffect(() => {
        const today = new Date()
        const isoDate = today.toISOString() // '2025-11-28T10:00:00Z'

        fetch(`http://localhost:8080/api/diary/day?timestamp=${isoDate}`, {
            headers: {
                'X-User-Id': userId,
            },
        })
            .then(res => res.json())
            .then(data => setPage(data))
            .catch(err => console.error('Ошибка при получении страницы дневника:', err))
    }, [])

    const date = new Date()
    const currentDateTime = formatDateTime(date)

    return (
        <div className={styles.header}>
            <div className={styles.headerTitle}>
                <h6 className={styles.headerTitle}>Дневник аллергии</h6>
            </div>

            <div className={styles.headerCalendar}>
                <div className={styles.headerCalendarDay}>
                    <button className={styles.headerCalendarDayBtn}>◀</button>
                    <div className={styles.headerCalendarDayTitles}>
                        <p className={styles.headerCalendarDayTitle}>
                            {currentDateTime.split(',').slice(0, -1)}
                        </p>
                        <p className={styles.headerCalendarDaySubtitle}>
                            {getDay(date)}
                        </p>
                    </div>
                    <button className={styles.headerCalendarDayBtn}>▶</button>
                </div>

                <div className={styles.headerCalendarDayItems}>
                    <div className={styles.headerCalendarDayItem}>
                        <h5>{page?.userSymptoms.length ?? 0}</h5>
                        <p className="headerSubtitle">Симптомов</p>
                    </div>
                    <div className={styles.headerCalendarDayItem}>
                        <h5>{page?.medicines.length ?? 0}</h5>
                        <p className="headerSubtitle">Лекарства</p>
                    </div>
                    <div className={styles.headerCalendarDayItem}>
                        <h5>{page?.userNotes ? 1 : 0}</h5>
                        <p className="headerSubtitle">Заметки</p>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default HeaderNotebook
