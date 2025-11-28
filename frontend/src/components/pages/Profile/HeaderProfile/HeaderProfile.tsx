import {useEffect, useState} from 'react'
import style from './HeaderProfile.module.css'

interface User {
    name: string
}

function HeaderProfile() {
    const [user, setUser] = useState<User | null>(null)

    useEffect(() => {
        const userId = 'b1d60e91-97e6-4659-ae7f-bde6808e2c4c'
        fetch(`http://localhost:8080/api/users/${userId}`)
            .then(res => res.json())
            .then(data => setUser(data))
            .catch(err => console.error('Ошибка при получении юзера:', err))
    }, [])

    return (
        <header className={style.header}>
            <div className={style.headerTitle}>
                <div className={style.headerTitleLink}>
                    <h1>Профиль</h1>
                </div>
            </div>


        </header>
    )


}

export default HeaderProfile
