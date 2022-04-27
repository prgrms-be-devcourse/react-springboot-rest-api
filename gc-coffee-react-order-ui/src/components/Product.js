// React는 props에 arguments를 객체로 전달해줌
import React from "@types/react";

export function Product(props) {
    const productName = props.productName;
    const category = props.category;
    const price = props.price;
    return (
        <>
            <div className="col-2">
                <img className="img-fluid" src="https://i.imgur.com/HKOFQYa.jpeg" alt=""/></div>
            <div className="col">
                <div className="row text-muted">{category}</div>
                {/* 이렇게 {}를 이용해서 변수애 접근할 수 있음. expression 이라고 하는 js 식이 들어감  */}
                <div className="row">{productName}</div>
            </div>
            <div className="col text-center price">{price}원</div>
            <div className="col text-end action">
                <button className="btn btn-small btn-outline-dark" href="">추가</button>
            </div>
        </>
    )
}