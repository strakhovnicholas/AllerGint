import { Link } from 'react-router-dom'
import FooterItem from '../../../../interfaces/FooterIcon-interface'
import style from './FooterIcon.module.css'

function FooterIcon({ link, svgPath, text, isActive }: FooterItem) {
  return (
    <Link to={link} className={style.footerIconWrapper}>
      <img
        src={svgPath}
        alt={text}
        width="24px"
        height="24px"
        className={
          isActive
            ? `${style.footerIconIcon} ${style.activeIcon}`
            : style.footerIconIcon
        }
      />

      <p
        className={
          isActive
            ? `${style.footerIconText} ${style.activeText}`
            : style.footerIconText
        }
      >
        {text}
      </p>
    </Link>
  )
}

export default FooterIcon
