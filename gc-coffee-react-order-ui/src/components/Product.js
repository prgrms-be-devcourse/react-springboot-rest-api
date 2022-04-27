// React는 props에 arguments를 객체로 전달해줌
import React from "react";

export function Product(props) {
    const productId = props.productId;
    const productName = props.productName;
    const category = props.category;
    const price = props.price;

    const handleAddBtnClicked = e => {
       props.onAddClick(productId);
    };

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
                {/*onClick={}안에 onClick이 일어났을 때 전달될 함수를 넣음*/}
                <button onClick={handleAddBtnClicked} className="btn btn-small btn-outline-dark" href="">추가</button>
            </div>
        </>
    )
}