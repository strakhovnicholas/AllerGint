import style from './ActionsHome.module.css'

function ActionsHome() {
   return (
      <div className="first card">
         <div className="title">Быстрые действия</div>
         <div className={style.actions}>
            <div className={style.cell}>
               <img
                  src="img/svg/pils.svg"
                  alt="Лекарства"
                  width="32px"
                  height="32px"
                  className={style.icon}
               />
               <p className={style.text}>Принять лекарство</p>
            </div>
            <div className={style.cell}>
               <img
                  src="img/svg/book.svg"
                  alt="Дневник"
                  width="32px"
                  height="32px"
                  className={style.icon}
               />
               <p className={style.text}>Дневник</p>
            </div>
            <div className={style.cell}>
               <img
                  src="img/svg/syringe.svg"
                  alt="АСИТ"
                  width="32px"
                  height="32px"
                  className={style.icon}
               />
               <p className={style.text}>АСИТ терапия</p>
            </div>
            <div className={style.cell}>
               <img
                  src="img/svg/map.svg"
                  alt="Карты"
                  width="32px"
                  height="32px"
                  className={style.icon}
               />
               <p className={style.text}>Карты пыльцы</p>
            </div>
         </div>
      </div>
   )
}

export default ActionsHome
