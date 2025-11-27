import { useState } from 'react'
import style from './FilterMap.module.css'

function FilterMap() {
   const [activeFilter, setActiveFilter] = useState('Все аллергены')

   const filters = ['Все аллергены', 'Берёза', 'Полынь', 'Злаки', 'Дуб']

   const handleFilterClick = (filterName: string) => {
      setActiveFilter(filterName)
   }

   return (
      <div className={`${style.filter} first`}>
         <div className={style.filterWrapper}>
            {filters.map((filter) => (
               <div
                  key={filter}
                  className={`${style.filterItem} ${
                     activeFilter === filter ? style.filterItemActive : ''
                  }`}
                  onClick={() => handleFilterClick(filter)}
               >
                  {filter}
               </div>
            ))}
         </div>
      </div>
   )
}

export default FilterMap
