import style from './LocationMap.module.css'

function LocationMap() {
   return (
      <div className="card">
         <div className="title">Ваше местоположение</div>
         <div className={style.pollenLevel}>Высокий риск</div>
         <div className={style.wrapper}>
            <div className={style.cell}>
               <div className={style.type}>
                  <img
                     src="img/svg/tree.svg"
                     alt="Вид"
                     width="24px"
                     height="24px"
                     className={style.icon}
                  />
                  <div className={style.typeText}>Pollen</div>
               </div>
               <div className={style.count}>80</div>
               <div className={style.countText}>зёрен на м</div>
            </div>
            <div className={style.cell}></div>
            <div className={style.cell}></div>
            <div className={style.cell}></div>
         </div>
         <div className={style.hint}>
            <div className={style.hintTitle}>
               <img
                  src="img/svg/lightbulb.svg"
                  alt="лампочка"
                  width="24px"
                  height="24px"
                  className={style.icon}
               />
               <div>Рекомендации</div>
            </div>
            <ul className={style.recomendations}>
               <li>Избегайте длительных прогулок на улице</li>
               <li>Закройте окна в доме и машине</li>
               <li>Примите антигистаминное средство</li>
               <li>Носите солнцезащитные очки</li>
            </ul>
         </div>
      </div>
   )
}

export default LocationMap
