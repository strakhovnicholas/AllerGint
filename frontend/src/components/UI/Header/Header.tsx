import React from 'react'
import styles from './Header.module.css'

function Header() {
  return (
    <div className={styles.header}>
      <div className="header_wrapper">
        <p>Welcoome</p>
        {/* <img src="../../img/aim.svg" alt="" /> */}
      </div>
    </div>
  )
}

export default Header
