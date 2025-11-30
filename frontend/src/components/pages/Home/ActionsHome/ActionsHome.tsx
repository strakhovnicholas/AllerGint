import { Link } from 'react-router-dom'
import style from './ActionsHome.module.css'

function ActionsHome() {
   return (
      <div className={style.actionsContainer}>
         <div className={style.actions}>
            <button className={style.actionButton}>
               <div className={style.iconWrapper}>
                  <img
                     src="img/svg/pils.svg"
                     alt="Лекарства"
                     className={style.icon}
                  />
               </div>
               <p className={style.text}>Учет приема лекарств</p>
            </button>
            <Link to="/notebook" className={style.actionButton}>
               <div className={style.iconWrapper}>
                  <img
                     src="img/svg/book.svg"
                     alt="Дневник"
                     className={style.icon}
                  />
               </div>
               <p className={style.text}>Дневник</p>
            </Link>
            <button className={style.actionButton}>
               <div className={style.iconWrapper}>
                  <img
                     src="img/svg/syringe.svg"
                     alt="АСИТ"
                     className={style.icon}
                  />
               </div>
               <p className={style.text}>АСИТ терапия</p>
            </button>
            <Link to="/map" className={style.actionButton}>
               <div className={style.iconWrapper}>
                  <img
                     src="img/svg/map.svg"
                     alt="Карты"
                     className={style.icon}
                  />
               </div>
               <p className={style.text}>Карта пыльцы</p>
            </Link>
         </div>
      </div>
   )
}

export default ActionsHome
