import style from './MedProfile.module.css'

function MedProfile() {
   const al = [
      { type: 'Полынь', level: 1 },
      { type: 'Берёза', level: 2 },
      { type: 'Дуб', level: 3 },
      { type: 'Полынь', level: 1 },
      { type: 'Полынь', level: 1 },
      { type: 'Полынь', level: 1 },
   ]

   const months = ['январь', 'февраль', 'март']

   const chastota = 'Редко'

   return (
      <div className="card">
         <div className="title">Мои аллергены</div>

         <div className={style.severity}>
            <div>
               Сезонность:{' '}
               {months.map((val, index) => {
                  return <span key={index}>{val}</span>
               })}
            </div>
            <div>
               Частота симптомов: <span>{chastota}</span>
            </div>
         </div>

         <ul className={style.data}>
            {al.map((val, index) => {
               return (
                  <li className={style.listItem} key={index}>
                     <div className={style.text}>{val.type}</div>
                     {val.level === 1 && (
                        <div className={style.weak}>слабо</div>
                     )}
                     {val.level === 2 && (
                        <div className={style.medium}>средне</div>
                     )}
                     {val.level === 3 && (
                        <div className={style.strong}>сильно</div>
                     )}
                  </li>
               )
            })}
         </ul>
         <button className={style.edit}>Добавить</button>
      </div>
   )
}

export default MedProfile
