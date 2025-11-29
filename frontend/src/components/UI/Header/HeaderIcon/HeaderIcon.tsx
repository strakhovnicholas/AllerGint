import { Link } from 'react-router-dom'
import HeaderItem from '../../../../interfaces/HeaderIcon-interface'
import style from './HeaderIcon.module.css'

function HeaderIcon({ link, svgPath, text, isActive }: HeaderItem) {
  return (
    <Link 
      to={link} 
      className={
        isActive
          ? `${style.headerIconWrapper} ${style.activeWrapper}`
          : style.headerIconWrapper
      }
    >
      <img
        src={svgPath}
        alt={text}
        width="24px"
        height="24px"
        className={
          isActive
            ? `${style.headerIconIcon} ${style.activeIcon}`
            : style.headerIconIcon
        }
      />

      <p
        className={
          isActive
            ? `${style.headerIconText} ${style.activeText}`
            : style.headerIconText
        }
      >
        {text}
      </p>
    </Link>
  )
}

export default HeaderIcon