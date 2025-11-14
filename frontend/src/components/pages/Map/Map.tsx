import FilterMap from './FilterMap/FilterMap'
import HeaderMap from './HeaderMap/HeaderMap'
import style from './Map.module.css'

function Map() {
   return (
      <>
         <HeaderMap />
         <div className={style.filter}>
            <FilterMap />
         </div>
      </>
   )
}

export default Map
