import './App.css';
import 'bootstrap/dist/css/bootstrap.css'
import React, { useState } from 'react';

// React는 props에 arguments를 객체로 전달해줌
function Product(props) {
    const productName = props.productName;
    const category = props.category;
    const price = props.price;
    return (
        <>
            <div className="col-2"><img className="img-fluid" src="https://i.imgur.com/HKOFQYa.jpeg"
                                        alt=""/></div>
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

// props 안에 products라는게 존재한다
// 이 products는 App()에서 전달해줌
function ProductList({ products = [] }) {
    return (
        <React.Fragment>
            <h5 className="flex-grow-0"><b>상품 목록</b></h5>
            <ul className="list-group products">
                {/* products 안에 있는 배열의 요소만큼 loop가 돌면서 변환하는 코드 */}
                {products.map(v =>
                    // loop를 돌때는 만드시 key가 필요함.
                    // v.id로 unique하게 이 product를 그리는 것임
                    <li key={v.id} className="list-group-item d-flex mt-3">
                        {/* 이렇게 파라미터를 전달할 수 있다 */}
                        <Product productName={v.productName} category={v.category} price={v.price}/>
                    </li>
                )}
            </ul>
        </React.Fragment>
    )
}

function SummaryItem(productName, count) {
    return (
        <div className="row">
            <h6 className="p-0">{productName} <span className="badge bg-dark text-">{count}개</span> </h6>
        </div>
    )
}
function Summary(items = []) {
    const totalPrice = items.reduce((prev, curr) => prev + (curr.price * curr.count), 0)

    return (
        <>
            <div>
                <h5 className="m-0 p-0"><b>Summary</b></h5>
            </div>
            <hr/>
            {items.map(v => <SummaryItem key={v.id} count={v.count} productName={v.productName}/>)}

            <form>
                <div className="mb-3">
                    <label htmlFor="email" className="form-label">이메일</label>
                    <input type="email" className="form-control mb-1" id="email"/>
                </div>
                <div className="mb-3">
                    <label htmlFor="address" className="form-label">주소</label>
                    <input type="text" className="form-control mb-1" id="address"/>
                </div>
                <div className="mb-3">
                    <label htmlFor="postcode" className="form-label">우편번호</label>
                    <input type="text" className="form-control" id="postcode"/>
                </div>
                <div>당일 오후 2시 이후의 주문은 다음날 배송을 시작합니다.</div>
            </form>
            <div className="row pt-2 pb-2 border-top">
                <h5 className="col">총금액</h5>
                <h5 className="col text-end">{totalPrice}원</h5>
            </div>
            <button className="btn btn-dark col-12">결제하기</button>
        </>
    )
}

// class형 component에서는 this.component로 상태에 접근할 수 있다
// 근데 함수형 component를 이용하면 hook을 이용해야 함
// class App extends Component {
//     render() {
//         this.state
//         return (
//             <div>
//
//             </div>
//         )
//     }
// }


function App() {
    const [products, setProducts] = useState([
        {id: 'uuid-1', productName: "콜롬비아 커비1", category: '커피빈', price: 5000 },
        {id: 'uuid-2', productName: "콜롬비아 커비2", category: '커피빈', price: 5000 },
        {id: 'uuid-3', productName: "콜롬비아 커비3", category: '커피빈', price: 5000 }
    ]);

    const [items, setItems] = useState([]);

    return (
        <div className="container-fluid">
            <div className="row justify-content-center m-4">
                <h1 className="text-center">Grids & Circle</h1>
            </div>
            <div className="card">
                <div className="row">
                    <div className="col-md-8 mt-4 d-flex flex-column align-items-start p-3 pt-0">
                        <ProductList products={products}/>
                    </div>
                    <div className="col-md-4 summary p-4">
                        <Summary items/>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default App;
