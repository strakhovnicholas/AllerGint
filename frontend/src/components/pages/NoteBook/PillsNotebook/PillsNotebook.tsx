import React, {useEffect, useState} from 'react'
import styles from './PillsNotebook.module.css'
import {Day, DoseMeasureType, MedicineDto, MedicineType} from '../../../../interfaces/Notebook-interface'

interface NewPill {
    medicineName: string
    dose: number
    doseMeasureType: DoseMeasureType
    medicineDescription?: string
    medicineType?: MedicineType
}

function PillsNotebook() {
    const [day, setDay] = useState<Day | null>(null)
    const [newPill, setNewPill] = useState<NewPill>({medicineName: '', dose: 0, doseMeasureType: DoseMeasureType.MG})
    const [editingPillId, setEditingPillId] = useState<string | null>(null)
    const [error, setError] = useState('')
    const [showForm, setShowForm] = useState(false)


    useEffect(() => {
        const userId = 'b1d60e91-97e6-4659-ae7f-bde6808e2c4c'
        const timestamp = new Date().toISOString()
        fetch(`http://localhost:8080/api/diary/day?timestamp=${timestamp}`, {
            headers: {'X-User-Id': userId}
        })
            .then(res => res.json())
            .then(data => setDay(data))
            .catch(err => console.error(err))
    }, [])

    const syncWithServer = (updatedDay: Day) => {
        const userId = 'b1d60e91-97e6-4659-ae7f-bde6808e2c4c'
        fetch(`http://localhost:8080/api/diary/e37b563d-6cd0-495f-8492-b244c89a993f`, {
            method: 'PATCH',
            headers: {'Content-Type': 'application/json', 'X-User-Id': userId},
            body: JSON.stringify(updatedDay)
        })
            .then(res => res.json())
            .then(data => setDay(data))
            .catch(err => console.error(err))
    }

    const handleAddOrUpdatePill = () => {
        if (!newPill.medicineName.trim() || newPill.dose <= 0) {
            setError('Введите корректное название и дозу лекарства')
            return
        }
        if (!day) return
        setError('')

        const pill: MedicineDto = {
            id: editingPillId ? editingPillId : crypto.randomUUID(),
            medicineName: newPill.medicineName,
            dose: newPill.dose,
            doseMeasureType: newPill.doseMeasureType,
            medicineDescription: newPill.medicineDescription || '',
            medicineType: newPill.medicineType || MedicineType.PILLS
        }

        let updatedMedicines: MedicineDto[]
        if (editingPillId) {
            updatedMedicines = day.medicines.map(m => m.id === editingPillId ? pill : m)
            setEditingPillId(null)
        } else {
            updatedMedicines = day.medicines ? [...day.medicines, pill] : [pill]
        }

        const updatedDay = {...day, medicines: updatedMedicines}
        setDay(updatedDay)
        syncWithServer(updatedDay)

        setNewPill({medicineName: '', dose: 0, doseMeasureType: DoseMeasureType.MG})
        setShowForm(false)
    }

    const handleEditPill = (pill: MedicineDto) => {
        setNewPill({
            medicineName: pill.medicineName,
            dose: pill.dose,
            doseMeasureType: pill.doseMeasureType,
            medicineDescription: pill.medicineDescription,
            medicineType: pill.medicineType
        })
        setEditingPillId(pill.id)
        setShowForm(true)
    }

    const handleDeletePill = (pillId: string) => {
        if (!day) return
        const updatedDay = {...day, medicines: day.medicines.filter(p => p.id !== pillId)}
        setDay(updatedDay)
        syncWithServer(updatedDay)
    }

    return (
        <div className={`card ${styles.cardPills}`}>
            <div className={styles.pillsTitle}>
                <p className="title">Принятые лекарства</p>
                <button className={styles.showFormBtn} onClick={() => setShowForm(!showForm)}>
                    {showForm ? 'Отменить' : '+ Добавить лекарство'}
                </button>
            </div>

            {day?.medicines && day.medicines.length > 0 ? (
                <div className={styles.pillsList}>
                    {day.medicines.map(pill => (
                        <div key={pill.id} className={styles.pillItem}>
                            <p>{pill.medicineName}</p>
                            <span>{pill.dose} {pill.doseMeasureType}</span>
                            <div className={styles.pillActions}>
                                <button className={styles.editBtn} onClick={() => handleEditPill(pill)}>✎</button>
                                <button className={styles.deleteBtn} onClick={() => handleDeletePill(pill.id)}>🗑
                                </button>
                            </div>
                        </div>
                    ))}
                </div>
            ) : (
                <p className={styles.noPills}>Нет принятых лекарств</p>
            )}


            {showForm && (
                <div className={styles.formOverlay}>
                    <div className={styles.pillAdd}>
                        {error && <p className={styles.error}>{error}</p>}
                        <input
                            type="text"
                            placeholder="Название лекарства"
                            value={newPill.medicineName}
                            onChange={e => setNewPill(prev => ({...prev, medicineName: e.target.value}))}
                        />
                        <input
                            type="number"
                            placeholder="Доза"
                            value={newPill.dose}
                            onChange={e => setNewPill(prev => ({...prev, dose: +e.target.value}))}
                        />
                        <select
                            value={newPill.doseMeasureType}
                            onChange={e => setNewPill(prev => ({
                                ...prev,
                                doseMeasureType: e.target.value as DoseMeasureType
                            }))}
                        >
                            <option value={DoseMeasureType.MG}>MG</option>
                            <option value={DoseMeasureType.ML}>ML</option>
                            <option value={DoseMeasureType.G}>G</option>
                        </select>
                        <button className={styles.pillAddBtn} onClick={handleAddOrUpdatePill}>
                            {editingPillId ? 'Сохранить изменения' : 'Добавить'}
                        </button>
                    </div>
                </div>
            )}
        </div>
    )

}

export default PillsNotebook
