// props 안에 products라는게 존재한다
// 이 products는 App()에서 전달해줌
import React from "react";
import {Product} from "./Product";

export function ProductList({products = [], onAddClick}) {
    return (
        <React.Fragment>
            <h5 className="flex-grow-0"><b>상품 목록</b></h5>
            <ul className="list-group products">
                {products.map(v =>
                    <li key={v.productId} className="list-group-item d-flex mt-3">
                        <Product {...v} onAddClick={onAddClick}/>
                    </li>
                )}
            </ul>
            {/*<ul className="list-group products">*/}
            {/*    /!* products 안에 있는 배열의 요소만큼 loop가 돌면서 변환하는 코드 *!/*/}
            {/*    {products.map(v =>*/}
            {/*        // loop를 돌때는 만드시 key가 필요함.*/}
            {/*        // v.id로 unique하게 이 product를 그리는 것임*/}
            {/*        <li key={v.productId} className="list-group-item d-flex mt-3">*/}
            {/*            /!* 이렇게 하면 안에 있는 내용이 다 나열 *!/*/}
            {/*            /!* ... : js의 Spread 연산자, *!/*/}
            {/*            <Product {...v} onAddClick={onAddClick}/>*/}
            {/*        </li>*/}
            {/*    )}*/}
            {/*</ul>*/}
        </React.Fragment>
    )
}