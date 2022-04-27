import '../App.css'
import 'bootstrap/dist/css/bootstrap.css'

export const Product = (props) => {
    const {productId, productName, category, price, onAddClick} = props

    const handleAddBtnClick = e => {
        onAddClick(productId);
    }

    return (
        <>
            <div className="col-2">
                <img className="img-fluid" src="https://i.imgur.com/HKOFQYa.jpeg" alt=""/>
            </div>
            <div className="col">
                <div className="row text-muted">{category}</div>
                <div className="row">{productName}</div>
            </div>
            <div className="col text-center price">{price}원</div>
            <div className="col text-end action">
                <button onClick={handleAddBtnClick} className="btn btn-small btn-outline-dark">추가</button>
            </div>
        </>
    )
}