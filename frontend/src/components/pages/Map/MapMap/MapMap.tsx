import style from './MapMap.module.css'

function MapMap() {
   return (
      <div className={style.map}>
         <div className={style.plusBtn}></div>
         <div className={style.minusBtn}></div>
      </div>
   )
}

export default MapMap
