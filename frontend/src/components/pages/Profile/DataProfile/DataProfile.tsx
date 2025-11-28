import { useEffect, useState } from 'react'
import style from './DataProfile.module.css'

type User = { username: string; gender: string; age: number; town: string }

function DataProfile() {
   const [user, setUser] = useState<User | null>(null)

   useEffect(() => {
      const userId = 'b1d60e91-97e6-4659-ae7f-bde6808e2c4c'
      fetch(`http://localhost:8080/api/users/${userId}`)
         .then(res => res.json())
         .then(data => setUser(data))
         .catch(err => console.error('Ошибка при получении данных юзера', err))
   }, [])

   if (!user) return <div>Загрузка...</div>

   return (
      <div className="card first">
         <div className="title">Ваши данные</div>

         <ul className={style.data}>
            <li>Имя: {user.username}</li>
            <li>Пол: {user.gender}</li>
            <li>Возраст: {user.age}</li>
         </ul>

         <button className={style.edit}>Изменить</button>
      </div>
   )
}

export default DataProfile
