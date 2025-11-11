import style from './HeaderMap.module.css'

function HeaderMap() {
   return (
      <header className={style.header}>
         <div className={style.headerContent}>
            <div className={style.headerTop}>
               <img
                  src="img/svg/row.svg"
                  alt="Стрелочка"
                  width="30px"
                  height="30px"
                  className={style.iconRow}
               />
               <h1 className={style.title}>Карта пыльцы</h1>
               <div className={style.icons}>
                  <div className={style.iconPlaceholder}>
                     <img
                        src="img/svg/aim.svg"
                        alt="цель"
                        width="26px"
                        height="26px"
                        className={style.icon}
                     />
                  </div>
                  <div className={style.iconPlaceholder}>
                     <img
                        src="img/svg/stack.svg"
                        alt="стак"
                        width="26px"
                        height="26px"
                        className={style.icon}
                     />
                  </div>
               </div>
            </div>
            <div className={style.headerBottom}>
               <div className={style.locationInfo}>
                  <p className={style.location}>Москва</p>
                  <p className={style.dateTime}>15 Мая 2024, 14:30</p>
               </div>
               <div className={style.updateInfo}>
                  <p className={style.updateText}>Обновлено</p>
                  <p className={style.updateTime}>5 мин назад</p>
               </div>
            </div>
         </div>
      </header>
   )
}

export default HeaderMap
