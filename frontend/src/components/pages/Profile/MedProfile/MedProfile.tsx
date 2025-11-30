import {useEffect, useState} from 'react'
import style from './MedProfile.module.css'

interface AllergenDto {
    id?: string
    name: string
    severity: 'STRONG' | 'MEDIUM' | 'WEAK'
    monthsOfManifestation: string[]
}

interface SymptomPreferenceDto {
    id?: string
    symptomName: string
    frequency: 'RARE' | 'MODERATE' | 'OFTEN'
}

interface User {
    name: string
    age: number
    gender: string
    town: string
    allergens: AllergenDto[]
    symptomPreferences: SymptomPreferenceDto[]
}

const severityMap = {STRONG: 'сильно', MEDIUM: 'средне', WEAK: 'слабо'}
const frequencyMap = {RARE: 'Редко', MODERATE: 'Средне', OFTEN: 'Часто'}
const monthMap: Record<string, string> = {
    JAN: 'Январь', FEB: 'Февраль', MAR: 'Март', APR: 'Апрель', MAY: 'Май', JUN: 'Июнь',
    JUL: 'Июль', AUG: 'Август', SEP: 'Сентябрь', OCT: 'Октябрь', NOV: 'Ноябрь', DEC: 'Декабрь',
}

export default function MedProfile() {
    const [user, setUser] = useState<User>({
        name: '', age: 0, gender: '', town: '', allergens: [], symptomPreferences: [],
    })
    const [modal, setModal] = useState<{ type: 'allergen' | 'symptom', index?: number } | null>(null)
    const [inputName, setInputName] = useState('')
    const [selectValue, setSelectValue] = useState<'STRONG' | 'MEDIUM' | 'WEAK' | 'RARE' | 'MODERATE' | 'OFTEN'>('MEDIUM')

    const userId = 'b1d60e91-97e6-4659-ae7f-bde6808e2c4c'

    useEffect(() => {
        fetch(`http://localhost:8080/api/users/${userId}`)
            .then(res => res.json())
            .then(data => setUser({
                name: data.name,
                age: data.age,
                gender: data.gender,
                town: data.town || 'Не указан',
                allergens: Array.isArray(data.allergens) ? data.allergens : [],
                symptomPreferences: Array.isArray(data.symptomPreferences) ? data.symptomPreferences : [],
            }))
            .catch(err => console.error(err))
    }, [])

    const patchUser = (payload: Partial<User>) => {
        fetch(`http://localhost:8080/api/users/${userId}`, {
            method: 'PATCH',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(payload),
        })
            .then(res => res.json())
            .then(data => setUser(data))
            .catch(err => console.error('Ошибка при обновлении:', err))
    }

    const openModal = (type: 'allergen' | 'symptom', index?: number) => {
        setModal({type, index})
        if (type === 'allergen') {
            if (index !== undefined) {
                const al = user.allergens[index]
                setInputName(al.name)
                setSelectValue(al.severity)
            } else {
                setInputName('')
                setSelectValue('MEDIUM')
            }
        } else {
            if (index !== undefined) {
                const sp = user.symptomPreferences[index]
                setInputName(sp.symptomName)
                setSelectValue(sp.frequency)
            } else {
                setInputName('')
                setSelectValue('MODERATE')
            }
        }
    }

    const saveModal = () => {
        if (!modal) return
        if (modal.type === 'allergen') {
            const updated = [...user.allergens]
            if (modal.index !== undefined) {
                updated[modal.index] = {
                    ...updated[modal.index],
                    name: inputName,
                    severity: selectValue as 'STRONG' | 'MEDIUM' | 'WEAK'
                }
            } else {
                updated.push({
                    name: inputName,
                    severity: selectValue as 'STRONG' | 'MEDIUM' | 'WEAK',
                    monthsOfManifestation: []
                })
            }
            patchUser({allergens: updated})
        } else {
            const updated = [...user.symptomPreferences]
            if (modal.index !== undefined) {
                updated[modal.index] = {
                    ...updated[modal.index],
                    symptomName: inputName,
                    frequency: selectValue as 'RARE' | 'MODERATE' | 'OFTEN'
                }
            } else {
                updated.push({symptomName: inputName, frequency: selectValue as 'RARE' | 'MODERATE' | 'OFTEN'})
            }
            patchUser({symptomPreferences: updated})
        }
        setModal(null)
    }

    const removeAllergen = (index: number) => {
        const updated = [...user.allergens]
        updated.splice(index, 1)
        patchUser({allergens: updated})
    }

    const removeSymptom = (index: number) => {
        const updated = [...user.symptomPreferences]
        updated.splice(index, 1)
        patchUser({symptomPreferences: updated})
    }

    return (
        <div className={style.profileWrapper}>
            <div className={`${style.card} ${style.userCard}`}>
                <div className={style.cardHeader}>Профиль пользователя</div>
                <div className={style.cardContent}>
                    <div>Имя: {user.name}</div>
                    <div>Возраст: {user.age}</div>
                    <div>Пол: {user.gender}</div>
                </div>
            </div>

            <div className={`${style.card} ${style.allergensCard}`}>
                <div className={style.cardHeader}>
                    Мои триггеры
                </div>
                <div className={style.cardContent}>
                    {user.allergens?.length ? (
                        <ul className={style.data}>
                            {user.allergens.map((al, idx) => (
                                <li className={style.listItem} key={al.id || idx}>
                                    <div className={style.listItemTop}>
                                        <span>{al.name} ({al.monthsOfManifestation.map(m => monthMap[m]).join(', ')})</span>
                                        <div>
                                            <button style={{
                                                backgroundColor: '#34a753',
                                                borderRadius: '50%',
                                                width: '32px',
                                                height: '32px',
                                                display: 'flex',
                                                alignItems: 'center',
                                                justifyContent: 'center'
                                            }} onClick={() => openModal('allergen', idx)}>
                                                <img src="/img/pencil-svgrepo-com.svg" alt="Редактировать"
                                                     style={{width: '20px', height: '20px'}}/>
                                            </button>
                                            <button style={{
                                                backgroundColor: '#34a753',
                                                borderRadius: '50%',
                                                width: '32px',
                                                height: '32px',
                                                display: 'flex',
                                                alignItems: 'center',
                                                justifyContent: 'center'
                                            }} onClick={() => removeAllergen(idx)}>
                                                <img src="/img/cross-svgrepo-com.svg" alt="Удалить"
                                                     style={{width: '20px', height: '20px'}}/>
                                            </button>
                                        </div>
                                    </div>
                                    <div className={`${style.listItemBottom} ${style[severityMap[al.severity]]}`}>
                                        {severityMap[al.severity]}
                                    </div>
                                </li>
                            ))}
                        </ul>
                    ) : <div>Триггеры не указаны</div>}
                </div>
            </div>

            <div className={`${style.card} ${style.symptomsCard}`}>
                <div className={style.cardHeader}>
                    Хронические/частые симптомы
                </div>
                <div className={style.cardContent}>
                    {user.symptomPreferences?.length ? (
                        <ul className={style.data}>
                            {user.symptomPreferences.map((sp, idx) => (
                                <li className={style.listItem} key={sp.id || idx}>
                                    <div className={style.listItemTop}>
                                        <span>{sp.symptomName}</span>
                                        <div>
                                            <button style={{
                                                backgroundColor: '#34a753',
                                                borderRadius: '50%',
                                                width: '32px',
                                                height: '32px',
                                                display: 'flex',
                                                alignItems: 'center',
                                                justifyContent: 'center'
                                            }} onClick={() => openModal('symptom', idx)}>
                                                <img src="/img/pencil-svgrepo-com.svg" alt="Редактировать"
                                                     style={{width: '20px', height: '20px'}}/>
                                            </button>
                                            <button style={{
                                                backgroundColor: '#34a753',
                                                borderRadius: '50%',
                                                width: '32px',
                                                height: '32px',
                                                display: 'flex',
                                                alignItems: 'center',
                                                justifyContent: 'center'
                                            }} onClick={() => removeSymptom(idx)}>
                                                <img src="/img/cross-svgrepo-com.svg" alt="Удалить"
                                                     style={{width: '20px', height: '20px'}}/>
                                            </button>
                                        </div>
                                    </div>
                                    <div className={`${style.listItemBottom} ${style[frequencyMap[sp.frequency]]}`}>
                                        {frequencyMap[sp.frequency]}
                                    </div>
                                </li>
                            ))}
                        </ul>
                    ) : <div>Симптомы не указаны</div>}
                </div>
            </div>

            {modal && (
                <div className={style.popup}>
                    <div className={style.popupContent}>
                        <input value={inputName} onChange={e => setInputName(e.target.value)}
                               placeholder={modal.type === 'allergen' ? 'Название триггера' : 'Название симптома'}/>
                        <select value={selectValue} onChange={e => setSelectValue(e.target.value as any)}>
                            {modal.type === 'allergen' ? (
                                <>
                                    <option value="STRONG">Сильно</option>
                                    <option value="MEDIUM">Средне</option>
                                    <option value="WEAK">Слабо</option>
                                </>
                            ) : (
                                <>
                                    <option value="RARE">Редко</option>
                                    <option value="MODERATE">Средне</option>
                                    <option value="OFTEN">Часто</option>
                                </>
                            )}
                        </select>
                        <div style={{display: 'flex', gap: '0.5rem'}}>
                            <button onClick={saveModal}>Сохранить</button>
                            <button onClick={() => setModal(null)}>Отмена</button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    )

}
