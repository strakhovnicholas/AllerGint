import {useState} from 'react'
import styles from './Footer.module.css'
import FooterIcon from './FooterIcon/FooterIcon'

function Footer() {
    const [activeIcon, setActiveIcon] = useState<string>('home')

    return (
        <div className={styles.footer}>
            <div className={styles.footerWrapper}>
                <div className={styles.footerWrapperList}>
                    <div onClick={() => setActiveIcon('home')}>
                        <FooterIcon
                            link="/"
                            svgPath="/img/svg/home.svg"
                            text="Главная"
                            isActive={activeIcon === 'home'}
                        />
                    </div>
                    <div onClick={() => setActiveIcon('notebook')}>
                        <FooterIcon
                            link="/notebook"
                            svgPath="/img/svg/book.svg"
                            text="Дневник"
                            isActive={activeIcon === 'notebook'}
                        />
                    </div>
                    <div onClick={() => setActiveIcon('map')}>
                        <FooterIcon
                            link="/map"
                            svgPath="/img/svg/map.svg"
                            text="Карта"
                            isActive={activeIcon === 'map'}
                        />
                    </div>
                    <div onClick={() => setActiveIcon('profile')}>
                        <FooterIcon
                            link="/profile"
                            svgPath="/img/svg/user.svg"
                            text="Профиль"
                            isActive={activeIcon === 'profile'}
                        />
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Footer
