import style from './NearPlacesMap.module.css'

function NearPlacesMap() {
   return (
      <div className="card">
         <div className="title">Ближайшие районы</div>
         <div className={style.district}>
            <div className={style.left}>
               <div className={style.name}>District name</div>
               <div className={style.direction}>to direction</div>
            </div>
            <div className={style.right}>
               <div className={style.level}>level</div>
               <div className={style.concentration}>n зерен/м³</div>
            </div>
         </div>
      </div>
   )
}

export default NearPlacesMap
