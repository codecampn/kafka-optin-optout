import Link from 'next/link';

export const NavigatinLogo = () => {
  return (
    <Link className="mr-16 flex items-center text-center" href={'/'}>
      <svg className="w-12" viewBox="0 0 705 484">
        <path
          fill="#368fa5"
          d="M419.6 251.5h-134l-67 116.1 67 116h134l66.9-116z"
        ></path>
        <path
          fill="#67adbe"
          d="M419.6.4h-134l-67 116 67 116.1h134l66.9-116.1z"
        ></path>
        <path
          fill="#00728c"
          d="M201.1 126H67.2L.2 242l67 116h133.9l67-116z"
        ></path>
        <path
          fill="#95c9d5"
          d="M637.8 126H503.9l-67 116 67 116h133.9l67-116z"
        ></path>
      </svg>
    </Link>
  );
};
