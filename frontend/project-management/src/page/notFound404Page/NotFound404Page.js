import React from 'react';
import "./not-found.css";
function NotFound404Page(props){
    return (
        <section className="page_error section-padding--lg bg--white">
        <div className="container">
            <div className="row">
                <div className="col-lg-12">
                    <div className="error__inner text-center">
                        <div className="error__logo">
                            <a href="#"><img src="/images/others/404.png" alt="error images" /></a>
                        </div>
                        <div className="error__content">
                            <h2>error - not found</h2>
                            <p>It looks like you are lost!</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    )
}

export default NotFound404Page;