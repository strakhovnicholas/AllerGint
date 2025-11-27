import style from './DataProfile.module.css'

type User = { username: string; gender: string; age: number; town: string }

function DataProfile() {
   let user: User = { username: 'User', gender: 'М', age: 25, town: 'moscow' }

   return (
      <div className="card first">
         <div className="title">Ваши данные</div>

         <ul className={style.data}>
            <li>Имя: {user.username}</li>
            <li>Пол: {user.gender}</li>
            <li>Возраст: {user.age}</li>
            <li>Город проживания: {user.town}</li>
         </ul>

         <button className={style.edit}>Изменить</button>
      </div>
   )
}

export default DataProfile
