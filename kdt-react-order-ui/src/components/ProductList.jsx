import '../App.css'
import 'bootstrap/dist/css/bootstrap.css'

import {Product} from "./Product";

export const ProductList = (props) => {
    const {products = [], onAddClick} = props;

    return (
        <>
            <h5 className="flex-grow-0">
                <b>상품 목록</b>
            </h5>
            <ul className="list-group products">
                {products.map((product) =>
                    <li key={product.id} className="list-group-item d-flex mt-3">
                        <Product {...product} onAddClick={onAddClick}/>
                    </li>
                )}
            </ul>
        </>
    )
}