import styles from './HeaderNotebook.module.css'
import { Link } from 'react-router-dom'

function HeaderNotebook() {
   return (
      <div className={styles.header}>
         <div className={styles.headerTitle}>
            <Link to="#" className={styles.headerTitleLink}>
               <svg
                  className={styles.headerTitleLinkSvg}
                  xmlns="http://www.w3.org/2000/svg"
                  viewBox="0 0 640 640"
                  width="27px"
                  height="28px"
               >
                  <path
                     fill="currentColor"
                     d="M566.6 342.6C579.1 330.1 579.1 309.8 566.6 297.3L406.6 137.3C394.1 124.8 373.8 124.8 361.3 137.3C348.8 149.8 348.8 170.1 361.3 182.6L466.7 288L96 288C78.3 288 64 302.3 64 320C64 337.7 78.3 352 96 352L466.7 352L361.3 457.4C348.8 469.9 348.8 490.2 361.3 502.7C373.8 515.2 394.1 515.2 406.6 502.7L566.6 342.7z"
                  />
               </svg>
            </Link>
            <h6 className={styles.headerTitle}>Дневник аллергии</h6>
            <button className={styles.headerTitleBtn}>
               <svg
                  xmlns="http://www.w3.org/2000/svg"
                  viewBox="0 0 640 640"
                  width="20px"
                  height="20px"
               >
                  <path
                     fill="currentColor"
                     d="M224 64C241.7 64 256 78.3 256 96L256 128L384 128L384 96C384 78.3 398.3 64 416 64C433.7 64 448 78.3 448 96L448 128L480 128C515.3 128 544 156.7 544 192L544 480C544 515.3 515.3 544 480 544L160 544C124.7 544 96 515.3 96 480L96 192C96 156.7 124.7 128 160 128L192 128L192 96C192 78.3 206.3 64 224 64zM320 256C306.7 256 296 266.7 296 280L296 328L248 328C234.7 328 224 338.7 224 352C224 365.3 234.7 376 248 376L296 376L296 424C296 437.3 306.7 448 320 448C333.3 448 344 437.3 344 424L344 376L392 376C405.3 376 416 365.3 416 352C416 338.7 405.3 328 392 328L344 328L344 280C344 266.7 333.3 256 320 256z"
                  />
               </svg>
            </button>
         </div>
         <div className={styles.headerCalendar}>
            <div className={styles.headerCalendarDay}>
               <button className={styles.headerCalendarDayBtn}>
                  <svg
                     xmlns="http://www.w3.org/2000/svg"
                     viewBox="0 0 640 640"
                     width="20px"
                  >
                     <path
                        fill="currentColor"
                        d="M169.4 297.4C156.9 309.9 156.9 330.2 169.4 342.7L361.4 534.7C373.9 547.2 394.2 547.2 406.7 534.7C419.2 522.2 419.2 501.9 406.7 489.4L237.3 320L406.6 150.6C419.1 138.1 419.1 117.8 406.6 105.3C394.1 92.8 373.8 92.8 361.3 105.3L169.3 297.3z"
                     />
                  </svg>
               </button>
               <p className={styles.headerCalendarDayTitle}>Число месяц год</p>
               <p
                  className={`${styles.headerCalendarDaySubtitle} headerSubtitle`}
               >
                  ^день недели^
               </p>
               <button className={styles.headerCalendarDayBtn}>
                  <svg
                     xmlns="http://www.w3.org/2000/svg"
                     viewBox="0 0 640 640"
                     width="20px"
                  >
                     <path
                        fill="currentColor"
                        d="M471.1 297.4C483.6 309.9 483.6 330.2 471.1 342.7L279.1 534.7C266.6 547.2 246.3 547.2 233.8 534.7C221.3 522.2 221.3 501.9 233.8 489.4L403.2 320L233.9 150.6C221.4 138.1 221.4 117.8 233.9 105.3C246.4 92.8 266.7 92.8 279.2 105.3L471.2 297.3z"
                     />
                  </svg>
               </button>
            </div>
            <div className={styles.headerCalendarDayItems}>
               <div className={styles.headerCalendarDayItem}>
                  <h5>7</h5>
                  <p className="headerSubtitle">Сипмтомов</p>
               </div>
               <div className={styles.headerCalendarDayItem}>
                  <h5>5</h5>
                  <p className="headerSubtitle">Триггера</p>
               </div>
               <div className={styles.headerCalendarDayItem}>
                  <h5>3</h5>
                  <p className="headerSubtitle">Лекарства</p>
               </div>
            </div>
         </div>
      </div>
   )
}

export default HeaderNotebook
