import {useState} from 'react'
import styles from './Survey.module.css'

interface Symptom {
    id: string
    name: string
    reaction: string
}

interface Trigger {
    id: string
    name: string
    reaction: string
}

interface HistoryEntry {
    id: string
    date: string
    situation: string
    symptoms: string
}

export default function Survey() {
    const [gender, setGender] = useState('')
    const [age, setAge] = useState<number | ''>('')

    const [symptoms, setSymptoms] = useState<Symptom[]>([])
    const [triggers, setTriggers] = useState<Trigger[]>([])
    const [history, setHistory] = useState<HistoryEntry[]>([
        {id: crypto.randomUUID(), date: '', situation: '', symptoms: ''}
    ])

    const [activeButtonId, setActiveButtonId] = useState<string | null>(null)

    const triggerButtonAnimation = (id: string, onClick: () => void) => {
        setActiveButtonId(id)
        onClick()
        setTimeout(() => setActiveButtonId(null), 600)
    }

    const renderAddButton = (id: string, onClick: () => void) => (
        <button
            onClick={() => triggerButtonAnimation(id, onClick)}
            className={activeButtonId === id ? styles.jumpFlip : ''}
        >
            +
        </button>
    )

    const addSymptom = () => {
        const id = crypto.randomUUID()
        setSymptoms(prev => [...prev, {id, name: '', reaction: ''}])
    }
    const removeSymptom = (id: string) => setSymptoms(prev => prev.filter(s => s.id !== id))

    const addTrigger = () => {
        const id = crypto.randomUUID()
        setTriggers(prev => [...prev, {id, name: '', reaction: ''}])
    }
    const removeTrigger = (id: string) => setTriggers(prev => prev.filter(t => t.id !== id))

    const addHistoryEntry = () => {
        const id = crypto.randomUUID()
        setHistory(prev => [...prev, {id, date: '', situation: '', symptoms: ''}])
    }
    const removeHistoryEntry = (id: string) => setHistory(prev => prev.filter(h => h.id !== id))

    const saveAnswers = () => {
        const payload = {gender, age, symptoms, triggers, history}
        console.log('Сохраняем ответы:', payload)
        alert('Ответы сохранены!')
    }

    return (
        <div className={styles.profileWrapper}>
            <div className={styles.card}>
                <div className={styles.cardHeader}>Личные данные</div>
                <div className={styles.cardContent}>
                    <label>
                        Пол
                        <select
                            className={styles.inputField}
                            value={gender}
                            onChange={e => setGender(e.target.value)}
                        >
                            <option value="">Выберите...</option>
                            <option value="MALE">Мужской</option>
                            <option value="FEMALE">Женский</option>
                            <option value="OTHER">Другой</option>
                        </select>
                    </label>
                    <label>
                        Возраст
                        <input
                            type="number"
                            className={styles.inputField}
                            value={age}
                            onChange={e => setAge(Number(e.target.value))}
                            placeholder="Например: 25"
                        />
                    </label>
                </div>
            </div>

            <div className={styles.card}>
                <div className={styles.cardHeader}>
                    Хронические симптомы и заболевания
                    {renderAddButton('symptom', addSymptom)}
                </div>
                <div className={styles.cardContent}>
                    <ul className={styles.data}>
                        {symptoms.map(s => (
                            <li key={s.id} className={styles.listItem}>
                                <div className={styles.listItemTop}>
                                    <input
                                        className={styles.inputField}
                                        placeholder="Название симптома/заболевания"
                                        value={s.name}
                                        onChange={e =>
                                            setSymptoms(prev =>
                                                prev.map(item =>
                                                    item.id === s.id
                                                        ? {...item, name: e.target.value}
                                                        : item
                                                )
                                            )
                                        }
                                    />
                                    <button onClick={() => removeSymptom(s.id)}>✕</button>
                                </div>
                                <textarea
                                    className={styles.textareaField}
                                    placeholder="Описание реакции (если знаете)"
                                    value={s.reaction}
                                    onChange={e =>
                                        setSymptoms(prev =>
                                            prev.map(item =>
                                                item.id === s.id
                                                    ? {...item, reaction: e.target.value}
                                                    : item
                                            )
                                        )
                                    }
                                />
                            </li>
                        ))}
                    </ul>
                </div>
            </div>

            <div className={styles.card}>
                <div className={styles.cardHeader}>
                    Аллергические триггеры
                    {renderAddButton('trigger', addTrigger)}
                </div>
                <div className={styles.cardContent}>
                    <ul className={styles.data}>
                        {triggers.map(t => (
                            <li key={t.id} className={styles.listItem}>
                                <div className={styles.listItemTop}>
                                    <input
                                        className={styles.inputField}
                                        placeholder="Название триггера"
                                        value={t.name}
                                        onChange={e =>
                                            setTriggers(prev =>
                                                prev.map(item =>
                                                    item.id === t.id
                                                        ? {...item, name: e.target.value}
                                                        : item
                                                )
                                            )
                                        }
                                    />
                                    <button onClick={() => removeTrigger(t.id)}>✕</button>
                                </div>
                                <textarea
                                    className={styles.textareaField}
                                    placeholder="Описание реакции (если знаете)"
                                    value={t.reaction}
                                    onChange={e =>
                                        setTriggers(prev =>
                                            prev.map(item =>
                                                item.id === t.id
                                                    ? {...item, reaction: e.target.value}
                                                    : item
                                            )
                                        )
                                    }
                                />
                            </li>
                        ))}
                    </ul>
                </div>
            </div>

            <div className={styles.card}>
                <div className={styles.cardHeader}>
                    История проявлений аллергии
                    {renderAddButton('history', addHistoryEntry)}
                </div>
                <div className={styles.cardContent}>
                    <ul className={styles.data}>
                        {history.map(h => (
                            <li key={h.id} className={styles.listItem}>
                                <div className={styles.listItemTop}>
                                    <input
                                        className={styles.inputField}
                                        type="date"
                                        value={h.date}
                                        onChange={e =>
                                            setHistory(prev =>
                                                prev.map(item =>
                                                    item.id === h.id
                                                        ? {...item, date: e.target.value}
                                                        : item
                                                )
                                            )
                                        }
                                    />
                                    <button onClick={() => removeHistoryEntry(h.id)}>✕</button>
                                </div>
                                <input
                                    className={styles.inputField}
                                    placeholder="Обстоятельства (улица, спортзал, еда, животные...)"
                                    value={h.situation}
                                    onChange={e =>
                                        setHistory(prev =>
                                            prev.map(item =>
                                                item.id === h.id
                                                    ? {...item, situation: e.target.value}
                                                    : item
                                            )
                                        )
                                    }
                                />
                                <textarea
                                    className={styles.textareaField}
                                    placeholder="Какие симптомы проявились?"
                                    value={h.symptoms}
                                    onChange={e =>
                                        setHistory(prev =>
                                            prev.map(item =>
                                                item.id === h.id
                                                    ? {...item, symptoms: e.target.value}
                                                    : item
                                            )
                                        )
                                    }
                                />
                            </li>
                        ))}
                    </ul>
                </div>
            </div>

            <div style={{display: 'flex', justifyContent: 'center', marginTop: '1.5rem'}}>
                <button
                    onClick={saveAnswers}
                    style={{
                        padding: '0.8rem 2rem',
                        fontSize: '1.25rem',
                        borderRadius: '0.7rem',
                        border: 'none',
                        background: 'linear-gradient(135deg, #66bb6a 0%, #34a753 100%)',
                        color: 'white',
                        cursor: 'pointer',
                        animation: 'bounceIn 0.5s ease'
                    }}
                >
                    Сохранить ответы
                </button>
            </div>
        </div>
    )

}
