import HeaderAsit from './HeaderAsit/HeaderAsit'
import style from './Asit.module.css'

function Asit() {
   return (
      <div>
         <HeaderAsit />

         <div className={style.content}>
            <div className="card first">
               <h3 className="title">АСИТ терапия</h3>
               <p className={style.description}>
                  Аллерген-специфическая иммунотерапия - эффективный метод
                  лечения аллергии
               </p>
            </div>

            <div className="card">
               <h4 className="title">Активные курсы</h4>
               <div className={style.therapyItem}>
                  <span className={style.therapyName}>Березовый экстракт</span>
                  <span className={style.therapyStatus}>Активная</span>
               </div>
            </div>

            <div className="card">
               <h4 className="title">Следующая инъекция</h4>
               <p className={style.nextInjection}>20 января 2024</p>
            </div>
         </div>
      </div>
   )
}

export default Asit
