import React, {useEffect, useRef, useState} from 'react';
import styles from './SymptomNotebook.module.css';
import {Day, UserSymptomDto} from '../../../../interfaces/Notebook-interface';

type SymptomStatus = 'STRONG' | 'MEDIUM' | 'WEAK' | null;

interface StatusButton {
    id: SymptomStatus;
    label: string;
}

interface UserSymptom {
    id: string;
    symptomName: string;
    symptomState: SymptomStatus;
    timestamp: string;
}

interface NewSymptom {
    symptomName: string;
    symptomState: SymptomStatus;
}

function SymptomNotebook() {
    const [day, setDay] = useState<Day | null>(null);
    const [loading, setLoading] = useState(true);
    const [saving, setSaving] = useState<{ [key: string]: boolean }>({});
    const [activeButtons, setActiveButtons] = useState<{ [key: string]: SymptomStatus }>({});
    const [clickTimes, setClickTimes] = useState<{ [key: string]: Date | null }>({});
    const [showModal, setShowModal] = useState(false);
    const [newSymptom, setNewSymptom] = useState<NewSymptom>({symptomName: '', symptomState: 'MEDIUM'});
    const [error, setError] = useState('');
    const modalRef = useRef<HTMLDivElement>(null);


    const statusButtons: StatusButton[] = [
        {id: 'STRONG', label: 'Сильно'},
        {id: 'MEDIUM', label: 'Умеренно'},
        {id: 'WEAK', label: 'Слабо'},
    ];

    const normalizeState = (state: string | null): SymptomStatus => {
        if (!state) return null;
        switch (state.toLowerCase()) {
            case 'strong':
                return 'STRONG';
            case 'medium':
                return 'MEDIUM';
            case 'weak':
                return 'WEAK';
            default:
                return null;
        }
    };


    const handleStatusClick = (symptomId: string, status: SymptomStatus) => {
        if (!day) return;
        const now = new Date();
        const userId = "b1d60e91-97e6-4659-ae7f-bde6808e2c4c";

        setActiveButtons(prev => ({...prev, [symptomId]: status}));
        setClickTimes(prev => ({...prev, [symptomId]: now}));
        setSaving(prev => ({...prev, [symptomId]: true}));

        const updatedSymptoms: ({
            id: string;
            symptomName: string;
            timestamp: string;
            symptomState: "strong" | "medium" | "weak" | null
        } | UserSymptomDto)[] = day.userSymptoms.map(sym =>
            sym.id === symptomId
                ? {...sym, symptomState: status, timestamp: sym.timestamp ?? now.toISOString()}
                : sym
        );

        const updatedPage = {...day, userSymptoms: updatedSymptoms};

        fetch(`http://localhost:8080/api/diary/e37b563d-6cd0-495f-8492-b244c89a993f`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
                'X-User-Id': userId
            },
            body: JSON.stringify(updatedPage),
        })
            .then(res => res.json())
            .then(data => {
                setDay(data);
                setSaving(prev => ({...prev, [symptomId]: false}));
            })
            .catch(err => {
                console.error("Ошибка при обновлении:", err);
                setSaving(prev => ({...prev, [symptomId]: false}));
            });
    };

    const handleAddSymptom = () => {
        if (!newSymptom.symptomName.trim()) {
            setError('Название симптома обязательно');
            return;
        }
        if (!day) return;
        setError('');
        const now = new Date();
        const userId = "b1d60e91-97e6-4659-ae7f-bde6808e2c4c";

        const newSymptomDto: UserSymptom = {
            id: crypto.randomUUID(),
            symptomName: newSymptom.symptomName.trim(),
            symptomState: normalizeState(newSymptom.symptomState),
            timestamp: now.toISOString(),
        };

        const updatedPage = {
            ...day,
            userSymptoms: [...day.userSymptoms, newSymptomDto],
        };

        fetch(`http://localhost:8080/api/diary/e37b563d-6cd0-495f-8492-b244c89a993f`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
                'X-User-Id': userId
            },
            body: JSON.stringify(updatedPage),
        })
            .then(res => res.json())
            .then(data => {
                setDay(data);
                setActiveButtons(prev => ({...prev, [newSymptomDto.id]: newSymptomDto.symptomState}));
                setClickTimes(prev => ({...prev, [newSymptomDto.id]: now}));
                setShowModal(true);

                // Прокрутка к модалке
                setTimeout(() => {
                    modalRef.current?.scrollIntoView({behavior: 'smooth', block: 'center'});
                }, 50);

                setNewSymptom({symptomName: '', symptomState: 'MEDIUM'});
            })
            .catch(err => console.error(err));
    };

    const getButtonClassName = (symptomId: string, status: SymptomStatus) =>
        `${styles.symptomListItemStatusBtn} ${activeButtons[symptomId] === status ? styles.symptomListItemStatusBtnActive : ''}`;

    const formatTime = (date: Date) => date.toLocaleTimeString('ru-RU', {hour: '2-digit', minute: '2-digit'});

    useEffect(() => {
        const userId = 'b1d60e91-97e6-4659-ae7f-bde6808e2c4c';
        const timestamp = new Date().toISOString();

        fetch(`http://localhost:8080/api/diary/day?timestamp=${timestamp}`, {
            headers: {'X-User-Id': userId}
        })
            .then(res => res.json())
            .then(data => {
                setDay(data);
                const defaultStates: { [key: string]: SymptomStatus } = {};
                const defaultTimes: { [key: string]: Date | null } = {};
                data.userSymptoms?.forEach((symptom: UserSymptom) => {
                    defaultStates[symptom.id] = normalizeState(symptom.symptomState);
                    defaultTimes[symptom.id] = symptom.timestamp ? new Date(symptom.timestamp) : null;
                });
                setActiveButtons(defaultStates);
                setClickTimes(defaultTimes);
            })
            .finally(() => setLoading(false));
    }, []);

    return (
        <div className={`card ${styles.cardSymptom}`}>
            {loading && <p>Загружаем ваш день...</p>}

            {!loading && (
                <>
                    <div className={styles.symptomTitle}>
                        <p className="title">Симптомы</p>
                        <button className={styles.symptomBtn} onClick={() => setShowModal(true)}>+ Добавить</button>
                    </div>

                    {day?.userSymptoms?.length ? (
                        [...day.userSymptoms].sort((a, b) => a.symptomName.localeCompare(b.symptomName)).map(symptom => (
                            <div key={symptom.id} className={styles.symptomListItem}>
                                <div className={styles.symptomListItemTitle}>
                                    <p className={styles.title}>{symptom.symptomName}</p>
                                </div>

                                <div className={styles.symptomListItemStatus}>
                                    {statusButtons.map(button => (
                                        <button
                                            key={button.id}
                                            className={getButtonClassName(symptom.id, button.id)}
                                            onClick={() => handleStatusClick(symptom.id, button.id)}
                                            disabled={saving[symptom.id]}
                                        >
                                            {button.label}
                                        </button>
                                    ))}
                                </div>

                                <div className={styles.symptomListItemTime}>
                                    <p>Время: <span>{clickTimes[symptom.id] ? formatTime(clickTimes[symptom.id]!) : '--:--'}</span>
                                    </p>
                                </div>

                                {saving[symptom.id] && <p className={styles.savingText}>Сохраняем...</p>}
                            </div>
                        ))
                    ) : <div>Нет симптомов</div>}

                    {showModal && (
                        <div ref={modalRef} className={styles.modalBackdrop}>
                            <div className={styles.modal}>
                                <input
                                    type="text"
                                    placeholder="Название симптома"
                                    value={newSymptom.symptomName}
                                    onChange={e => setNewSymptom(prev => ({...prev, symptomName: e.target.value}))}
                                />
                                <div className={styles.statusButtonsModal}>
                                    {statusButtons.map(btn => (
                                        <button
                                            key={btn.id}
                                            className={btn.id === newSymptom.symptomState ? styles.symptomListItemStatusBtnActive : styles.symptomListItemStatusBtn}
                                            onClick={() => setNewSymptom(prev => ({...prev, symptomState: btn.id}))}
                                        >
                                            {btn.label}
                                        </button>
                                    ))}
                                </div>
                                {error && <p className={styles.errorText}>{error}</p>}
                                <div className={styles.modalActions}>
                                    <button onClick={handleAddSymptom} className={styles.symptomBtn}>Сохранить</button>
                                    <button onClick={() => setShowModal(false)} className={styles.symptomBtn}>Отмена
                                    </button>
                                </div>
                            </div>
                        </div>
                    )}
                </>
            )}
        </div>
    );


}

export default SymptomNotebook;
