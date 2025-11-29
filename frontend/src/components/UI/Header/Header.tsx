import styles from "./Header.module.css"
import { Link, useLocation } from "react-router-dom"

function Header() {
  const location = useLocation()
  const isProfileActive = location.pathname === "/profile"

  return (
    <header className={styles.header}>
      <div className={styles.headerWrapper}>
        <div className={styles.headerContent}>
          {/* Left section: Logo + Navigation */}
          <div className={styles.leftSection}>
            <Link to="/" className={styles.logoLink}>
              <div className={styles.logo}>
                <img src="/img/logo.png" alt="Health App" className={styles.logoIcon} />
              </div>
            </Link>

            <nav className={styles.nav}>
              <Link
                to="/notebook"
                className={
                  location.pathname === "/notebook" ? `${styles.navLink} ${styles.navLinkActive}` : styles.navLink
                }
              >
                Дневник
              </Link>
              <Link
                to="/map"
                className={location.pathname === "/map" ? `${styles.navLink} ${styles.navLinkActive}` : styles.navLink}
              >
                Карта
              </Link>
            </nav>
          </div>

          {/* Profile button on the right */}
          <Link
            to="/profile"
            className={isProfileActive ? `${styles.profileButton} ${styles.profileButtonActive}` : styles.profileButton}
          >
            <img src="/img/svg/user.svg" alt="Профиль" className={styles.profileIcon} />
            <span className={styles.profileText}>Профиль</span>
          </Link>
        </div>
      </div>
    </header>
  )
}

export default Header